/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.wiki.engines.jspwiki;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.WikiException;
import com.ecyrd.jspwiki.WikiPage;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.engines.WikiEngine;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletURL;

/**
 * @author Jorge Ferrer
 */
public class JSPWikiEngine implements WikiEngine {

	public static String decodeJSPWikiName(String jspWikiName) {
		return StringUtil.replace(
			jspWikiName, _JSP_WIKI_NAME_2, _JSP_WIKI_NAME_1);
	}

	public String convert(
			com.liferay.portlet.wiki.model.WikiPage page,
			PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException {

		try {
			return convert(page);
		}
		catch (WikiException we) {
			throw new PageContentException(we);
		}
	}

	public Map<String, Boolean> getOutgoingLinks(
			com.liferay.portlet.wiki.model.WikiPage page)
		throws PageContentException {

		if (Validator.isNull(page.getContent())) {
			return Collections.emptyMap();
		}

		try {
			LiferayJSPWikiEngine engine = getEngine(page.getNodeId());

			WikiPage jspWikiPage = LiferayPageProvider.toJSPWikiPage(
				page, engine);

			Collection<String> titles = engine.scanWikiLinks(
				jspWikiPage, _encodeJSPWikiContent(page.getContent()));

			Map<String, Boolean> links = new HashMap<String, Boolean>();

			for (String title : titles) {
				if (title.startsWith("[[")) {
					title = title.substring(2);
				}
				else if (title.startsWith("[")) {
					title = title.substring(1);
				}

				if (title.endsWith("]]")) {
					title = title.substring(title.length() - 2, title.length());
				}
				else if (title.startsWith("[")) {
					title = title.substring(title.length() - 1, title.length());
				}

				Boolean existsObj = links.get(title);

				if (existsObj == null) {
					if (WikiPageLocalServiceUtil.getPagesCount(
							page.getNodeId(), title, true) > 0) {

						existsObj = Boolean.TRUE;
					}
					else {
						existsObj = Boolean.FALSE;
					}

					links.put(title, existsObj);
				}
			}

			return links;
		}
		catch (SystemException se) {
			throw new PageContentException(se);
		}
		catch (WikiException we) {
			throw new PageContentException(we);
		}
	}

	public void setInterWikiConfiguration(String interWikiConfiguration) {
	}

	public void setMainConfiguration(String mainConfiguration) {
		setProperties(mainConfiguration);
	}

	public boolean validate(long nodeId, String newContent) {
		return true;
	}

	protected String convert(com.liferay.portlet.wiki.model.WikiPage page)
		throws WikiException {

		String content = _encodeJSPWikiContent(page.getContent());

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		com.ecyrd.jspwiki.WikiEngine engine = getEngine(page.getNodeId());

		WikiPage jspWikiPage = LiferayPageProvider.toJSPWikiPage(page, engine);

		WikiContext wikiContext = new WikiContext(engine, jspWikiPage);

		return _decodeJSPWikiContent(engine.textToHTML(wikiContext, content));
	}

	protected LiferayJSPWikiEngine getEngine(long nodeId)
		throws WikiException {

		LiferayJSPWikiEngine engine = _engines.get(nodeId);

		if (engine != null) {
			return engine;
		}

		synchronized (_engines) {
			engine = _engines.get(nodeId);

			if (engine != null) {
				return engine;
			}

			Properties nodeProperties = new Properties(_properties);

			nodeProperties.setProperty("nodeId", String.valueOf(nodeId));

			String appName = nodeProperties.getProperty(
				"jspwiki.applicationName");

			nodeProperties.setProperty(
				"jspwiki.applicationName", appName + " for node " + nodeId);

			engine = new LiferayJSPWikiEngine(nodeProperties);

			_engines.put(nodeId, engine);

			return engine;
		}
	}

	protected synchronized void setProperties(String configuration) {
		_properties = new Properties();

		InputStream is = new UnsyncByteArrayInputStream(
			configuration.getBytes());

		try {
			_properties.load(is);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
	}

	private static String _decodeJSPWikiContent(String jspWikiContent) {
		return StringUtil.replace(
			jspWikiContent, _JSP_WIKI_NAME_2, _JSP_WIKI_NAME_1);
	}

	private static String _encodeJSPWikiContent(String content) {

		StringBundler encodedContent = new StringBundler();

		Matcher commentMatcher = _wikiCommentPattern.matcher(content);

		int start = 0;
		int end = 0;

		String oldContent = StringPool.BLANK;
		String newContent = StringPool.BLANK;

		while (commentMatcher.find()) {
			end = commentMatcher.start();

			oldContent = content.substring(start, end);

			Matcher wikiLinkMatcher = _wikiLinkPattern.matcher(oldContent);

			newContent = oldContent;

			while (wikiLinkMatcher.find()) {
				String link = wikiLinkMatcher.group();
				String linkValues = wikiLinkMatcher.group(1);

				String name = linkValues;
				String url = linkValues;

				int pos = linkValues.indexOf(CharPool.PIPE);

				if (pos != -1) {
					name = linkValues.substring(pos + 1, linkValues.length());
					url = linkValues.substring(0, pos);
				}

				String newLink =
					"[[" + _encodeJSPWikiName(url) + "|" + name + "]]";

				newContent = StringUtil.replace(newContent, link, newLink);
			}

			encodedContent.append(newContent);
			encodedContent.append(
				content.substring(
					commentMatcher.start(), commentMatcher.end()));

			start = commentMatcher.end();
		}

		if (start != content.length()) {
			encodedContent.append(content.substring(start));
		}

		return encodedContent.toString();
	}

	private static String _encodeJSPWikiName(String name) {
		if (name == null) {
			return StringPool.BLANK;
		}

		return StringUtil.replace(name, _JSP_WIKI_NAME_1, _JSP_WIKI_NAME_2);
	}

	private static final String[] _JSP_WIKI_NAME_1 = {
		StringPool.APOSTROPHE, StringPool.AT, StringPool.CARET,
		StringPool.EXCLAMATION, StringPool.INVERTED_EXCLAMATION,
		StringPool.INVERTED_QUESTION, StringPool.GRAVE_ACCENT,
		StringPool.QUESTION, StringPool.SLASH, StringPool.STAR
	};

	private static final String[] _JSP_WIKI_NAME_2 = {
		"__APO__", "__AT__", "__CAR__", "__EXM__", "__INE__", "__INQ__",
		"__GRA__", "__QUE__", "__SLA__", "__STA__"
	};

	private static Log _log = LogFactoryUtil.getLog(JSPWikiEngine.class);

	private static Pattern _wikiCommentPattern = Pattern.compile(
		"[\\{]{3,3}(.*?)[\\}]{3,3}", Pattern.DOTALL);
	private static Pattern _wikiLinkPattern = Pattern.compile(
		"[\\[]{2,2}(.+?)[\\]]{2,2}", Pattern.DOTALL);

	private Map<Long, LiferayJSPWikiEngine> _engines =
		new ConcurrentHashMap<Long, LiferayJSPWikiEngine>();
	private Properties _properties;

}