/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/tags/sakai-10.0/tests/src/java/org/sakaiproject/tool/assessment/test/integration/context/spring/TestFactoryUtil.java $
 * $Id: TestFactoryUtil.java 106463 2012-04-02 12:20:09Z david.horwitz@uct.ac.za $
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

package org.sakaiproject.tool.assessment.test.integration.context.spring;

import junit.framework.*;
import org.sakaiproject.tool.assessment.integration.context.spring.*;
import org.sakaiproject.tool.assessment.integration.context.*;
import org.springframework.test.AbstractTransactionalSpringContextTests;

public class TestFactoryUtil extends AbstractTransactionalSpringContextTests {
  private FactoryUtil factoryUtil = null;

  public TestFactoryUtil(String name) {
  }

  protected void onSetUpInTransaction() throws Exception {
  }

  protected String[] getConfigLocations() {

      String[] configLocations = {"org/sakaiproject/spring/applicationContext.xml"};

      return configLocations;
  }


  public void testLookup() throws Exception {
    IntegrationContextFactory actualReturn = factoryUtil.lookup();
    assertNotNull(actualReturn);
  }

  public static void main(String[] args)
  {
    System.out.println("starting.");
    TestFactoryUtil test = new TestFactoryUtil("test");
    try
    {
      test.setUp();
      test.testLookup();
      test.tearDown();
    }
    catch (Exception ex)
    {
      System.out.println("ex="+ex);
    }
    System.out.println("done.");
  }

}
