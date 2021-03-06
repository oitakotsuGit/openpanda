/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/reset-pass/tags/sakai-10.6/account-validator-api/src/java/org/sakaiproject/accountvalidator/logic/ValidationException.java $
 * $Id: ValidationException.java 105078 2012-02-24 23:00:38Z ottenhoff@longsight.com $
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

package org.sakaiproject.accountvalidator.logic;

public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ValidationException() {
		super();
		
	}
	
	public ValidationException(String message) {
		super(message);
	}
	
	
	public ValidationException(String message, Throwable cause) {
		super(message);
		super.initCause(cause);
	}
	
}
