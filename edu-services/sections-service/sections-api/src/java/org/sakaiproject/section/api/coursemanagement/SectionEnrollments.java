/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/edu-services/tags/sakai-10.6/sections-service/sections-api/src/java/org/sakaiproject/section/api/coursemanagement/SectionEnrollments.java $
 * $Id: SectionEnrollments.java 105077 2012-02-24 22:54:29Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2005, 2006, 2008 The Sakai Foundation
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
package org.sakaiproject.section.api.coursemanagement;

import java.util.Set;

/**
 * Encapsulates the section enrollments in a course for a set of students.  This
 * interface is an attempt to avoid using out of band agreements to send complex
 * maps or other data structures between the back-end managers and the UI.
 * 
 * @author <a href="mailto:jholtzman@berkeley.edu">Josh Holtzman</a>
 *
 */
public interface SectionEnrollments {
	public CourseSection getSection(String studentUid, String categoryId);
	
	public Set getStudentUuids();
}

