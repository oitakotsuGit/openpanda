/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/common/tags/sakai-10.6/archive-api/src/main/java/org/sakaiproject/importer/api/SakaiArchive.java $
 * $Id: SakaiArchive.java 133338 2014-01-16 17:17:12Z matthew.buckett@it.ox.ac.uk $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2008 The Sakai Foundation
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

package org.sakaiproject.importer.api;

import java.util.Collection;

import org.sakaiproject.archive.api.ImportMetadata;

public interface SakaiArchive {

	void buildSourceFolder(Collection<ImportMetadata> fnlList);
	String getSourceFolder();
}
