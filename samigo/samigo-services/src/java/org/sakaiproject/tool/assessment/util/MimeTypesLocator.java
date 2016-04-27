/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/tags/sakai-10.7/samigo-services/src/java/org/sakaiproject/tool/assessment/util/MimeTypesLocator.java $
 * $Id: MimeTypesLocator.java 319083 2015-05-20 22:24:13Z enietzel@anisakai.com $
 ***********************************************************************************
 *
 * Copyright (c) 2005, 2006, 2008, 2009 The Sakai Foundation
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


package org.sakaiproject.tool.assessment.util;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
 
public class MimeTypesLocator{

  private static MimeTypesLocator instance = null;
  private static MimetypesFileTypeMap map = null;

  public static MimeTypesLocator getInstance(){
	  return new MimeTypesLocator();
   }

  public static void setMimetypesFileTypeMap(MimetypesFileTypeMap map){
    MimeTypesLocator.map = map;
  } 

  public MimetypesFileTypeMap getMimetypesFileTypeMap(){
    return map;
  }

  public String getContentType(File file){
    return map.getContentType(file);
  }

}




