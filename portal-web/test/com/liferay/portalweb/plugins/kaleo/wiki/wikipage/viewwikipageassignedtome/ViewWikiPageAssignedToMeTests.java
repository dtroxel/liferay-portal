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

package com.liferay.portalweb.plugins.kaleo.wiki.wikipage.viewwikipageassignedtome;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWikiPageAssignedToMeTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(ConfigureWikiPageSingleApproverTest.class);
		testSuite.addTestSuite(AddPageWikiTest.class);
		testSuite.addTestSuite(AddPortletWikiTest.class);
		testSuite.addTestSuite(AddWikiPageTest.class);
		testSuite.addTestSuite(AssignToMeWikiPageActionsTest.class);
		testSuite.addTestSuite(ViewWikiPageAssignedToMeTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_ViewWikiPageAssignedToMeTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownWikiNodeTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(TearDownWorkflowConfigurationTest.class);

		return testSuite;
	}

}