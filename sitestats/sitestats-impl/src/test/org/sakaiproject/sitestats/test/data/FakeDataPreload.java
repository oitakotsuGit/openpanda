/**
 * $URL: https://source.sakaiproject.org/svn/sitestats/tags/sakai-10.7/sitestats-impl/src/test/org/sakaiproject/sitestats/test/data/FakeDataPreload.java $
 * $Id: FakeDataPreload.java 105078 2012-02-24 23:00:38Z ottenhoff@longsight.com $
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
package org.sakaiproject.sitestats.test.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.AbstractTransactionalSpringContextTests;


public class FakeDataPreload extends AbstractTransactionalSpringContextTests {
	private static Log	log	= LogFactory.getLog(FakeDataPreload.class);

	public void init() {
		log.info("FakeDataPreload.init()");
	}
}
