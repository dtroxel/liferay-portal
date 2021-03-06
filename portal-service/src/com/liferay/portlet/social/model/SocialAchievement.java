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

package com.liferay.portlet.social.model;

/**
 * @author Zsolt Berentey
 */
public interface SocialAchievement {

	public String getDescriptionKey();

	public String getIcon();

	public String getName();

	public String getNameKey();

	public void initialize(SocialActivityDefinition activityDefinition);

	public void processActivity(SocialActivity activity);

	public void setIcon(String icon);

	public void setName(String name);

	public void setProperty(String name, String value);

}