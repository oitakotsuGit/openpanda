/**
 * $Id: TestInterfaceFour.java 105077 2012-02-24 22:54:29Z ottenhoff@longsight.com $
 * $URL: https://source.sakaiproject.org/svn/entitybroker/tags/sakai-10.0/utils/src/test/org/sakaiproject/entitybroker/util/reflect/TestInterfaceFour.java $
 * TestInterfaceFour.java - entity-broker - May 5, 2008 1:29:50 PM - azeckoski
 **************************************************************************
 * Copyright (c) 2008 The Sakai Foundation
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
 */

package org.sakaiproject.entitybroker.util.reflect;

/**
 * Test interface that extends 4 others
 * 
 * @author Aaron Zeckoski (aaron@caret.cam.ac.uk)
 */
public interface TestInterfaceFour extends TestInterfaceOne, Runnable, Cloneable, Readable {

}
