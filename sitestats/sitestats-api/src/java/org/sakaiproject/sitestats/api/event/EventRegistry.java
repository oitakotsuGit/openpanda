/**
 * $URL: https://source.sakaiproject.org/svn/sitestats/tags/sakai-10.7/sitestats-api/src/java/org/sakaiproject/sitestats/api/event/EventRegistry.java $
 * $Id: EventRegistry.java 105078 2012-02-24 23:00:38Z ottenhoff@longsight.com $
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
package org.sakaiproject.sitestats.api.event;

import java.util.List;


public interface EventRegistry {

	/**
	 * Get the statisticable event registry.
	 * @return A list of all tool events definition.
	 * @see org.sakaiproject.sitestats.api.ToolInfo
	 */
	public List<ToolInfo> getEventRegistry();
	
	/** Check whether EventRegistry has expired. This means that getEventRegistry() will be called again, when needed. */
	public boolean isEventRegistryExpired();
	
	/** Get the event name (localized) for the specified event id. */
	public String getEventName(String eventId);
}
