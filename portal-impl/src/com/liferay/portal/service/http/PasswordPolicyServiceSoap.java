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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.PasswordPolicyServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portal.service.PasswordPolicyServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.PasswordPolicySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.PasswordPolicy}, that is translated to a
 * {@link com.liferay.portal.model.PasswordPolicySoap}. Methods that SOAP cannot
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
 * @see       PasswordPolicyServiceHttp
 * @see       com.liferay.portal.model.PasswordPolicySoap
 * @see       com.liferay.portal.service.PasswordPolicyServiceUtil
 * @generated
 */
public class PasswordPolicyServiceSoap {
	public static com.liferay.portal.model.PasswordPolicySoap addPasswordPolicy(
		java.lang.String name, java.lang.String description,
		boolean changeable, boolean changeRequired, long minAge,
		boolean checkSyntax, boolean allowDictionaryWords, int minAlphanumeric,
		int minLength, int minLowerCase, int minNumbers, int minSymbols,
		int minUpperCase, boolean history, int historyCount,
		boolean expireable, long maxAge, long warningTime, int graceLimit,
		boolean lockout, int maxFailure, long lockoutDuration,
		long resetFailureCount, long resetTicketMaxAge)
		throws RemoteException {
		try {
			com.liferay.portal.model.PasswordPolicy returnValue = PasswordPolicyServiceUtil.addPasswordPolicy(name,
					description, changeable, changeRequired, minAge,
					checkSyntax, allowDictionaryWords, minAlphanumeric,
					minLength, minLowerCase, minNumbers, minSymbols,
					minUpperCase, history, historyCount, expireable, maxAge,
					warningTime, graceLimit, lockout, maxFailure,
					lockoutDuration, resetFailureCount, resetTicketMaxAge);

			return com.liferay.portal.model.PasswordPolicySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deletePasswordPolicy(long passwordPolicyId)
		throws RemoteException {
		try {
			PasswordPolicyServiceUtil.deletePasswordPolicy(passwordPolicyId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.PasswordPolicySoap updatePasswordPolicy(
		long passwordPolicyId, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minAlphanumeric, int minLength,
		int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
		boolean history, int historyCount, boolean expireable, long maxAge,
		long warningTime, int graceLimit, boolean lockout, int maxFailure,
		long lockoutDuration, long resetFailureCount, long resetTicketMaxAge)
		throws RemoteException {
		try {
			com.liferay.portal.model.PasswordPolicy returnValue = PasswordPolicyServiceUtil.updatePasswordPolicy(passwordPolicyId,
					name, description, changeable, changeRequired, minAge,
					checkSyntax, allowDictionaryWords, minAlphanumeric,
					minLength, minLowerCase, minNumbers, minSymbols,
					minUpperCase, history, historyCount, expireable, maxAge,
					warningTime, graceLimit, lockout, maxFailure,
					lockoutDuration, resetFailureCount, resetTicketMaxAge);

			return com.liferay.portal.model.PasswordPolicySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PasswordPolicyServiceSoap.class);
}