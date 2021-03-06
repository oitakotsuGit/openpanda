/**********************************************************************************
* $URL: https://source.sakaiproject.org/svn/osp/tags/sakai-10.6/jsf/example/src/java/example/TestSubBean.java $
* $Id: TestSubBean.java 105079 2012-02-24 23:08:11Z ottenhoff@longsight.com $
***********************************************************************************
*
 * Copyright (c) 2005, 2006, 2008 The Sakai Foundation
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
package example;

import javax.faces.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: John Ellis
 * Date: Dec 28, 2005
 * Time: 4:49:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestSubBean {

   private String index = "" + Math.random();

   public void processTestButton(ActionEvent event) {
      int i = 1;

      i++;
   }

   public String getIndex() {
      return index;
   }

   public void setIndex(String index) {
      this.index = index;
   }

}
