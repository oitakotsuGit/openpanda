/**********************************************************************************
* $URL: https://source.sakaiproject.org/svn/chat/tags/sakai-10.6/chat-tool/tool/src/java/org/sakaiproject/chat2/tool/DecoratedSynopticOptions.java $
* $Id: DecoratedSynopticOptions.java 105079 2012-02-24 23:08:11Z ottenhoff@longsight.com $
***********************************************************************************
*
 * Copyright (c) 2007, 2008 The Sakai Foundation
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

/**
 * 
 */
package org.sakaiproject.chat2.tool;

/**
 * @author chrismaurer
 *
 */
public class DecoratedSynopticOptions {

   int days;
   int items;
   int chars;
   /**
    * @return the chars
    */
   public int getChars() {
      return chars;
   }
   /**
    * @param chars the chars to set
    */
   public void setChars(int chars) {
      this.chars = chars;
   }
   /**
    * @return the days
    */
   public int getDays() {
      return days;
   }
   /**
    * @param days the days to set
    */
   public void setDays(int days) {
      this.days = days;
   }
   /**
    * @return the items
    */
   public int getItems() {
      return items;
   }
   /**
    * @param items the items to set
    */
   public void setItems(int items) {
      this.items = items;
   }
   
   
}
