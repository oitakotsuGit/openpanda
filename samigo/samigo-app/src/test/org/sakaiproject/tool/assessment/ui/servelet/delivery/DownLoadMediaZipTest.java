/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/tags/sakai-10.6/samigo-app/src/test/org/sakaiproject/tool/assessment/ui/servelet/delivery/DownLoadMediaZipTest.java $
 * $Id: DownLoadMediaZipTest.java 108724 2012-05-30 10:03:22Z david.horwitz@uct.ac.za $
 ***********************************************************************************
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 The Sakai Foundation
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


package org.sakaiproject.tool.assessment.ui.servelet.delivery;

import org.sakaiproject.tool.assessment.ui.servlet.delivery.DownloadAllMediaServlet;

import junit.framework.TestCase;

public class DownLoadMediaZipTest extends TestCase  {
	
	
	public void testUniqueFileName() {
		DownloadAllMediaServlet serv = new DownloadAllMediaServlet();
		
		String fileName = "document.doc";
		
		String fileOut1 = serv.getUniqueFilename(fileName);
		String fileOut2 = serv.getUniqueFilename(fileName);
		String fileOut3 = serv.getUniqueFilename(fileName);
		
		assertNotSame(fileOut1, fileOut2);
		assertEquals("document-1.doc", fileOut2);
		assertEquals("document-2.doc", fileOut3);
		assertEquals(fileName, fileOut1);
	}

}
