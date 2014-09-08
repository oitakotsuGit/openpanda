/**
 * $URL: https://source.sakaiproject.org/svn/sitestats/branches/sakai-10.x/sitestats-api/src/java/org/sakaiproject/sitestats/api/StatsAuthz.java $
 * $Id: StatsAuthz.java 105078 2012-02-24 23:00:38Z ottenhoff@longsight.com $
 *
 * Copyright (c) 2006-2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.sitestats.api;

public interface StatsAuthz {

	/** Permissions */
	public static final String	PERMISSION_SITESTATS_VIEW		= "sitestats.view";
	public static final String	PERMISSION_SITESTATS_ADMIN_VIEW	= "sitestats.admin.view";

	// ################################################################
	// Public methods
	// ################################################################
	/**
	 * Check if current user has permission to access SiteStats tool.
	 * @param siteId The site id to check against.
	 */
	public boolean isUserAbleToViewSiteStats(String siteId);

	/**
	 * Check if current user has permission to access SiteStats Admin tool.
	 * @param siteId The site id to check against.
	 */
	public boolean isUserAbleToViewSiteStatsAdmin(String siteId);

	/** Check if current tool is an instance of the SiteStats tool. */
	public boolean isSiteStatsPage();
	
	/** Check if current tool is an instance of the SiteStats Admin tool. */
	public boolean isSiteStatsAdminPage();
}