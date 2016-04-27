/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/tags/sakai-10.7/samigo-services/src/test/org/sakaiproject/assessment/facade/test/ItemFacadeTest.java $
 * $Id: ItemFacadeTest.java 106463 2012-04-02 12:20:09Z david.horwitz@uct.ac.za $
 ***********************************************************************************
 *
 * Copyright (c) 2004, 2005, 2006, 2008 The Sakai Foundation
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


package org.sakaiproject.assessment.facade.test;

import org.hibernate.SessionFactory;
import org.sakaiproject.tool.assessment.facade.ItemFacade;
import org.sakaiproject.tool.assessment.facade.ItemFacadeQueries;
import org.springframework.test.AbstractTransactionalSpringContextTests;

public class ItemFacadeTest extends AbstractTransactionalSpringContextTests {
	
	protected String[] getConfigLocations() {
		return new String[] {"/spring-hibernate.xml"};
	}

	ItemFacadeQueries queries;
	
	protected void onSetUpInTransaction() throws Exception {
		queries = new ItemFacadeQueries();
		queries.setSessionFactory((SessionFactory)applicationContext.getBean("sessionFactory"));

	}

	public void testGetIem() {
		/*
		 * We expect a item that doesn't exist to return null
		 * not to escalate an exception
		 */
		try {
		ItemFacade item = queries.getItem(99999999L);
		assertNull(item);
		} catch (Exception e) {
			fail("unexpected exception");
		}
		
	}
	
	
}
