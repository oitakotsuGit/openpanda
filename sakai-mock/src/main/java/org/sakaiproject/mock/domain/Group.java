/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sakai-mock/tags/sakai-10.6/src/main/java/org/sakaiproject/mock/domain/Group.java $
 * $Id: Group.java 105077 2012-02-24 22:54:29Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2007, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/
package org.sakaiproject.mock.domain;

import org.sakaiproject.site.api.Site;

public class Group extends AuthzGroup implements org.sakaiproject.site.api.Group {
	private static final long serialVersionUID = 1L;

	String title;
	Site site;

	public Group(Site site) {
		this.site = site;
	}
	
	public Site getContainingSite() {
		return site;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
