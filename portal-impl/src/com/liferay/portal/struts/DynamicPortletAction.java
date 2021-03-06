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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.util.InstancePool;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicPortletAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ModuleConfig moduleConfig = getModuleConfig(actionRequest);

		mapping = (ActionMapping)moduleConfig.findActionConfig(
			getPath(actionRequest));

		PortletAction action = (PortletAction)InstancePool.get(
			mapping.getType());

		action.processAction(
			mapping, form, portletConfig, actionRequest, actionResponse);
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ModuleConfig moduleConfig = getModuleConfig(renderRequest);

		mapping = (ActionMapping)moduleConfig.findActionConfig(
			getPath(renderRequest));

		PortletAction action = (PortletAction)InstancePool.get(
			mapping.getType());

		return action.render(
			mapping, form, portletConfig, renderRequest, renderResponse);
	}

	protected String getPath(PortletRequest portletRequest) throws Exception {
		return null;
	}

}