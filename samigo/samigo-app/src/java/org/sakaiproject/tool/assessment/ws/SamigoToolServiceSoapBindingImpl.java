/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/tags/sakai-10.0/samigo-app/src/java/org/sakaiproject/tool/assessment/ws/SamigoToolServiceSoapBindingImpl.java $
 * $Id: SamigoToolServiceSoapBindingImpl.java 106463 2012-04-02 12:20:09Z david.horwitz@uct.ac.za $
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



/**
 * SamigoToolServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 *
 * This was part of the web services demo files. The rest wsdl2java autogenerated files 
 * are deleted, along with wsdl, deploy/undeploy wsdd files and server-config.wsdd. 
 * Kept this Impl class.
 */

package org.sakaiproject.tool.assessment.ws;


public class SamigoToolServiceSoapBindingImpl implements org.sakaiproject.tool.assessment.ws.SamigoTool{
    public org.sakaiproject.tool.assessment.ws.Item[] search(java.lang.String in0) throws java.rmi.RemoteException {
        SamigoToolWebService samigows= new SamigoToolWebService();
        return samigows.getItemObjArrayByKeyword(in0);
    }

    public java.lang.String download(java.lang.String[] in0, java.lang.String qtiVersion) throws java.rmi.RemoteException {
        SamigoToolWebService samigows= new SamigoToolWebService();
        return samigows.download(in0, qtiVersion);
    }

}
