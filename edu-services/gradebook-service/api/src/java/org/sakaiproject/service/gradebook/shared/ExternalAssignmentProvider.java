/**********************************************************************************
*
* $Id: ExternalAssignmentProvider.java 126908 2013-07-11 02:37:39Z nbotimer@unicon.net $
*
***********************************************************************************
*
 * Copyright (c) 2011 The Sakai Foundation
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
package org.sakaiproject.service.gradebook.shared;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Provider model to integrate external assessment sources with the gradebook.
 * Its present use is to provide advice about activity grouping and visibility
 * so that learners are not shown activities in which they cannot participate.
 * 
 * https://jira.sakaiproject.org/browse/SAK-19668
 * @since 2.9.0
 */
public interface ExternalAssignmentProvider {

	/**
	 * Get the application identifier for this provider. This must
	 * be unique to the external tool/service for proper operation.
	 */
	String getAppKey();

	/**
	 * Check if an assignment/assessment exists with the given identifier.
	 * This will be in the form that is supplied to the Gradebook, so it
	 * will need to be parsed. If the prefix is not recognized as matching
	 * for this service, false is expected to be returned, even if there
	 * may be an assignment with the prefixed id.
	 * Note that this may seem strange but it is required because services
	 * do not currently register their prefixes with the Gradebook, so the
	 * external IDs are completely opaque and do not identify their source.
	 */
	boolean isAssignmentDefined(String id);

	/**
	 * Check if the given assignment is grouped.
	 * Note that this will be a prefixed ID and must be parsed.
	 *
	 * @param id The prefixed external ID as registered with Gradebook
	 */
	boolean isAssignmentGrouped(String id);

	/**
	 * Check if the given assignment is visible to the given user. The primary
	 * use of this check is to see if groups are in effect for the assignment
	 * and whether or not the user can see the item.
	 *
	 * @param id The prefixed external ID as registered with Gradebook
	 * @param userId The internal user ID for the user to check
	 */
	boolean isAssignmentVisible(String id, String userId);

	/**
	 * Retrieve all assignments for a gradebook that are marked as externally
	 * maintained and are visible to the current user.
	 *
	 * @param gradebookUid The gradebook's unique identifier
	 * @return A list of IDs (as for externalId) of assignments visible to the current user
	 */
	List<String> getExternalAssignmentsForCurrentUser(String gradebookUid);

	/**
	 * Retrieve all assignments for a gradebook that are marked as externally
	 * maintained and are (potentially variably) visible to a set of users.
	 *
	 * @param gradebookUid The gradebook's unique identifier
	 * @param studentIds The collection of user IDs to search/filter for
	 * @return A map of Student ID to list of external IDs of assignments visible to each user
	 */
	Map<String, List<String>> getAllExternalAssignments(String gradebookUid, Collection<String> studentIds);
}

