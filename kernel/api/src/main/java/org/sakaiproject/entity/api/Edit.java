/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/kernel/tags/kernel-1.3.3/api/src/main/java/org/sakaiproject/entity/api/Edit.java $
 * $Id: Edit.java 51317 2008-08-24 04:38:02Z csev@umich.edu $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2008 Sakai Foundation
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

package org.sakaiproject.entity.api;

/**
 * <p>
 * Edit is a mutable Entity.
 * </p>
 */
public interface Edit extends Entity
{
	/**
	 * Check to see if the edit is still active, or has already been closed.
	 * 
	 * @return true if the edit is active, false if it's been closed.
	 */
	boolean isActiveEdit();

	/**
	 * Access the resource's properties for modification
	 * 
	 * @return The resource's properties.
	 */
	ResourcePropertiesEdit getPropertiesEdit();
}
