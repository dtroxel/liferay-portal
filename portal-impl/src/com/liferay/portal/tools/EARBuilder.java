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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

/**
 * @author Brian Wing Shun Chan
 * @author Zsigmond Rab
 */
public class EARBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 2) {
			new EARBuilder(args[0], StringUtil.split(args[1]));
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public EARBuilder(String originalApplicationXML, String[] pluginFileNames) {
		try {
			Document document = SAXReaderUtil.read(
				new File(originalApplicationXML));

			Element rootElement = document.getRootElement();

			for (String pluginFileName : pluginFileNames) {
				Element moduleElement = rootElement.addElement("module");

				Element webElement = moduleElement.addElement("web");

				Element webURIElement = webElement.addElement("web-uri");

				webURIElement.addText(pluginFileName);

				Element contextRootElement = webElement.addElement(
					"context-root");

				String contextRoot = _getContextRoot(pluginFileName);

				contextRootElement.addText(contextRoot);
			}

			FileUtil.write(
				originalApplicationXML, document.formattedString(), true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String _getContextRoot(String pluginFileName) {
		String contextRoot = pluginFileName;

		int pos = contextRoot.lastIndexOf(".war");

		if (pos != -1) {
			contextRoot = contextRoot.substring(0, pos);
		}

		if (contextRoot.equals("liferay-portal")) {
			contextRoot = PropsValues.PORTAL_CTX;

			if (contextRoot.equals(StringPool.SLASH)) {
				contextRoot = StringPool.BLANK;
			}
			else if (contextRoot.startsWith(StringPool.SLASH)) {
				contextRoot = contextRoot.substring(1);
			}
		}

		return StringPool.SLASH.concat(contextRoot);
	}

}