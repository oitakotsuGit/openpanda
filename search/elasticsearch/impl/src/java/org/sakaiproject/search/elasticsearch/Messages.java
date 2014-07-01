/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/search/branches/sakai-10.x/elasticsearch/impl/src/java/org/sakaiproject/search/elasticsearch/Messages.java $
 * $Id: Messages.java 120867 2013-03-06 22:27:02Z jbush@rsmart.com $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2007, 2008 The Sakai Foundation
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

package org.sakaiproject.search.elasticsearch;

import org.sakaiproject.util.ResourceLoader;

/**
 * @author ieb
 *
 */
public class Messages
{
	private static final String BUNDLE_NAME = "org.sakaiproject.search.elastic.bundle.Messages"; //$NON-NLS-1$

	private static final ResourceLoader RESOURCE_BUNDLE = new ResourceLoader(BUNDLE_NAME);

	private Messages()
	{
	}

	public static String getString(String key)
	{
		return RESOURCE_BUNDLE.getString(key);
	}
}
