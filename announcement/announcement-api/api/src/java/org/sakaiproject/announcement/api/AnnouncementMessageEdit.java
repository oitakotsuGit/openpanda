/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/announcement/tags/announcement-2.9.1/announcement-api/api/src/java/org/sakaiproject/announcement/api/AnnouncementMessageEdit.java $
 * $Id: AnnouncementMessageEdit.java 59673 2009-04-03 23:02:03Z arwhyte@umich.edu $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.announcement.api;

import org.sakaiproject.message.api.MessageEdit;

/**
 * <p>
 * AnnouncementMessageEdit is an editable AnnouncementMessage.
 * </p>
 */
public interface AnnouncementMessageEdit extends AnnouncementMessage, MessageEdit
{
	/**
	 * A (AnnouncementMessageHeaderEdit) cover for getHeaderEdit to access the announcement message header.
	 * 
	 * @return The announcement message header.
	 */
	public AnnouncementMessageHeaderEdit getAnnouncementHeaderEdit();
}
