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

package com.liferay.portlet.dynamicdatamapping.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.dynamicdatamapping.model.DDMTemplate}, that is translated to a
 * {@link com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/api/secure/axis. Set the property
 * <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMTemplateServiceHttp
 * @see       com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap
 * @see       com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil
 * @generated
 */
public class DDMTemplateServiceSoap {
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap[] copyTemplates(
		long structureId, long newStructureId, java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> returnValue =
				DDMTemplateServiceUtil.copyTemplates(structureId,
					newStructureId, type, serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteTemplate(long templateId)
		throws RemoteException {
		try {
			DDMTemplateServiceUtil.deleteTemplate(templateId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap getTemplate(
		long templateId) throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.getTemplate(templateId);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap[] getTemplates(
		long structureId, java.lang.String type, java.lang.String mode)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> returnValue =
				DDMTemplateServiceUtil.getTemplates(structureId, type, mode);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDMTemplateServiceSoap.class);
}