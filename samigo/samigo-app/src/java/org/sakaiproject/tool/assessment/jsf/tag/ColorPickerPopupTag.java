/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/tags/samigo-2.9.1/samigo-app/src/java/org/sakaiproject/tool/assessment/jsf/tag/ColorPickerPopupTag.java $
 * $Id: ColorPickerPopupTag.java 59684 2009-04-03 23:33:27Z arwhyte@umich.edu $
 ***********************************************************************************
 *
 * Copyright (c) 2004, 2005, 2006, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/



package org.sakaiproject.tool.assessment.jsf.tag;

import javax.faces.webapp.UIComponentTag;

/**
 * <p> </p>
 * <p>Description:<br />
 * This class is the tag handler that evaluates the color picker popup
 * custom tag.</p>
 * <p>Based on example code by Sun Microsystems. </p>
 * <p>Organization: Sakai Project</p>
 * @author Ed Smiley
 * @version $Id: ColorPickerPopupTag.java 59684 2009-04-03 23:33:27Z arwhyte@umich.edu $
 */

public class ColorPickerPopupTag extends UIComponentTag
{

  public String getComponentType()
  {
    return ("javax.faces.Output");
  }

  public String getRendererType()
  {
    return "ColorPickerPopup";
  }

}
