/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/branches/sakai-10.x/samigo-hibernate/src/java/org/sakaiproject/tool/assessment/data/dao/assessment/ItemAttachment.java $
 * $Id: ItemAttachment.java 106463 2012-04-02 12:20:09Z david.horwitz@uct.ac.za $
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

package org.sakaiproject.tool.assessment.data.dao.assessment;

import org.sakaiproject.tool.assessment.data.ifc.assessment.ItemAttachmentIfc;
import org.sakaiproject.tool.assessment.data.ifc.assessment.ItemDataIfc;
import java.io.Serializable;
import java.util.Date;


public class ItemAttachment
    extends AttachmentData
    implements Serializable, ItemAttachmentIfc
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -7845686301407065500L;
private ItemDataIfc item;

  public ItemAttachment()  { }

  public ItemAttachment(Long attachmentId, ItemDataIfc item, String resourceId,
                   String filename, String mimeType,
                   Long fileSize, String description, String location,
                   Boolean isLink, Integer status, String createdBy, Date createdDate,
                   String lastModifiedBy, Date lastModifiedDate){
    super(attachmentId, resourceId, ItemAttachmentIfc.ITEM_ATTACHMENT,
          filename, mimeType,
          fileSize, description, location, isLink, status,
          createdBy, createdDate, lastModifiedBy, lastModifiedDate);
    this.item = item;
  }

  public ItemAttachment(Long attachmentId, String resourceId,
                   String filename, String mimeType,
                   Long fileSize, String description, String location,
                   Boolean isLink, Integer status, String createdBy, Date createdDate,
                   String lastModifiedBy, Date lastModifiedDate){
    super(attachmentId, resourceId, ItemAttachmentIfc.ITEM_ATTACHMENT,
          filename, mimeType,
          fileSize, description, location, isLink, status,
          createdBy, createdDate, lastModifiedBy, lastModifiedDate);
  }

  public ItemDataIfc getItem() {
    return item;
  }

  public void setItem(ItemDataIfc item) {
    this.item = item;
  }

  public Long getAttachmentType() {
    return ItemAttachmentIfc.ITEM_ATTACHMENT;
  }

  public void setAttachmentType(Long attachmentType) {
  }


}
