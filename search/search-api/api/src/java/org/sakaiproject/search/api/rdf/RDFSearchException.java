/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/search/tags/sakai-10.6/search-api/api/src/java/org/sakaiproject/search/api/rdf/RDFSearchException.java $
 * $Id: RDFSearchException.java 105078 2012-02-24 23:00:38Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2006, 2008 The Sakai Foundation
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
package org.sakaiproject.search.api.rdf;


/**
 * @author ieb
 *
 */
public class RDFSearchException extends RDFException
{

	/**
	 * 
	 */
	public RDFSearchException()
	{
		super();
	}

	/**
	 * @param arg0
	 */
	public RDFSearchException(String arg0)
	{
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public RDFSearchException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public RDFSearchException(Throwable arg0)
	{
		super(arg0);
	}

}
