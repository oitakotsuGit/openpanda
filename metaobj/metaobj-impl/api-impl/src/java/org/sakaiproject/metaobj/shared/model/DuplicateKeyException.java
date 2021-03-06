/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/metaobj/tags/sakai-10.6/metaobj-impl/api-impl/src/java/org/sakaiproject/metaobj/shared/model/DuplicateKeyException.java $
 * $Id: DuplicateKeyException.java 105079 2012-02-24 23:08:11Z ottenhoff@longsight.com $
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

package org.sakaiproject.metaobj.shared.model;

public class DuplicateKeyException extends PersistenceException {

   /**
    * @param errorCode
    * @param errorInfo
    * @param field
    */
   public DuplicateKeyException(String errorCode, Object[] errorInfo, String field) {
      super(errorCode, errorInfo, field);
      // TODO Auto-generated constructor stub
   }
}
