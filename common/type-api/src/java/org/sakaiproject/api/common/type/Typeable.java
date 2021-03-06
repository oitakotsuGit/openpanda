/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/common/tags/sakai-10.6/type-api/src/java/org/sakaiproject/api/common/type/Typeable.java $
 * $Id: Typeable.java 105077 2012-02-24 22:54:29Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006 Sakai Foundation
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

package org.sakaiproject.api.common.type;

/**
 * A generic interface for any object that is typed using the common {@link org.sakaiproject.service.common.type.Type}.
 * 
 * @author <a href="mailto:lance@indiana.edu">Lance Speelmon</a>
 */
public interface Typeable
{
	/**
	 * @return Returns the type.
	 */
	public Type getType();

	/**
	 * @param type
	 *        The type to set.
	 */
	public void setType(Type type);
}
