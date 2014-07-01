/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/search/tags/sakai-10.0/search-impl/impl/src/test/org/sakaiproject/search/indexer/impl/test/JournalTests.java $
 * $Id: JournalTests.java 105078 2012-02-24 23:00:38Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2007, 2008 The Sakai Foundation
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

package org.sakaiproject.search.indexer.impl.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author ieb
 *
 */
public class JournalTests
{

	public static Test suite()
	{
		TestSuite suite = new TestSuite(
				"Test for org.sakaiproject.search.indexer.impl.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(SequenceGeneratorDisabled.class);
		suite.addTestSuite(SearchBuilderItemSerializerTest.class);
		suite.addTestSuite(TransactionalIndexWorkerTest.class);
		suite.addTestSuite(ConcurrentIndexManagerTest.class);
		suite.addTestSuite(DbJournalManagerTest.class);
		suite.addTestSuite(MergeUpdateOperationTest.class);
		suite.addTestSuite(LoadSaveSegmentListTest.class);
		suite.addTestSuite(JournalOptimzationOperationTest.class);
		suite.addTestSuite(OptimizeOperationTest.class);
		//$JUnit-END$
		return suite;
	}

}
