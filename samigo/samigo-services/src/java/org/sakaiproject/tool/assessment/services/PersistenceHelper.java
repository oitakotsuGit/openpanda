/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/tags/sakai-10.6/samigo-services/src/java/org/sakaiproject/tool/assessment/services/PersistenceHelper.java $
 * $Id: PersistenceHelper.java 319083 2015-05-20 22:24:13Z enietzel@anisakai.com $
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

package org.sakaiproject.tool.assessment.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility methods for helping with persistence
 * @author dhorwitz
 * @since 10.0
 */
public class PersistenceHelper {
	
	private Log log = LogFactory.getLog(PersistenceHelper.class);
	
	
	private Integer deadlockInterval; // in ms

    public void setDeadlockInterval(Integer deadlockInterval){
      this.deadlockInterval = deadlockInterval;
    }

    public Integer getDeadlockInterval(){
      return deadlockInterval;
    }
	
	
	public int retryDeadlock(Exception e, int retryCount){
		log.warn("Error saving to db...retry again....");
		String errorMessage = e.getMessage();
		log.warn(errorMessage);
		int index = errorMessage.indexOf("ORA-00060"); // deadlock
		int index2 = errorMessage.indexOf("SQL state [61000]"); // oracle deadlock
		int index3 = errorMessage.indexOf("SQL state [41000]"); // mysql deadlock
		if (index > -1 || index2 > -1 || index3 > -1){
			retryCount--;
			log.info("deadlock detected (" + e.getMessage() + ") retry count is now" + retryCount);
			try {
				int ideadlockInterval = deadlockInterval.intValue();
				Thread.currentThread().sleep(ideadlockInterval);
			}
			catch(InterruptedException ex){
				log.warn(ex.getMessage());
			}
		}
		else retryCount = 0;
		return retryCount;
	}

	
	private Integer retryCount; // in ms

    public void setRetryCount(Integer retryCount){
      this.retryCount = retryCount;
    }

    public Integer getRetryCount(){
      return retryCount;
    }
	
}
