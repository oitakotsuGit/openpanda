/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/citations/tags/sakai-10.6/citations-osid/xserver/src/java/org/sakaibrary/osid/repository/xserver/SourceTitlePartStructure.java $
 * $Id: SourceTitlePartStructure.java 105079 2012-02-24 23:08:11Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2006, 2007, 2008 The Sakai Foundation
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

package org.sakaibrary.osid.repository.xserver;

public class SourceTitlePartStructure
implements org.osid.repository.PartStructure
{
	private static final org.apache.commons.logging.Log LOG =
		org.apache.commons.logging.LogFactory.getLog(
				"org.sakaibrary.osid.repository.xserver.SourceTitlePartStructure" );

    private org.osid.shared.Id SOURCE_TITLE_PART_STRUCTURE_ID = null;
    private org.osid.shared.Type type = new Type("sakaibrary", "partStructure",
    		"sourceTitle", "Source Title" );
    private String displayName = "Source Title";
    private String description = "Indicates the title of the source that " +
    		"contains the current resource.";
    private boolean mandatory = false;
    private boolean populatedByRepository = false;
    private boolean repeatable = false;
    
	private static SourceTitlePartStructure sourceTitlePartStructure;

    private SourceTitlePartStructure()
    {
        try
        {
            this.SOURCE_TITLE_PART_STRUCTURE_ID = Managers.getIdManager().getId(
            		"s230984njks089234980234890eh032kg");
        }
        catch (Throwable t)
        {
        	LOG.warn( "SourceTitlePartStructure() failed to get partStructure id: "
					+ t.getMessage() );
        }
    }

	protected static synchronized SourceTitlePartStructure getInstance()
	{
		if( sourceTitlePartStructure == null ) {
			sourceTitlePartStructure = new SourceTitlePartStructure();
		}
		return sourceTitlePartStructure;
	}
	
    public String getDisplayName()
    throws org.osid.repository.RepositoryException
    {
        return this.displayName;
    }

    public String getDescription()
    throws org.osid.repository.RepositoryException
    {
        return this.description;
    }

    public boolean isMandatory()
    throws org.osid.repository.RepositoryException
    {
        return this.mandatory;
    }

    public boolean isPopulatedByRepository()
    throws org.osid.repository.RepositoryException
    {
        return this.populatedByRepository;
    }

    public boolean isRepeatable()
    throws org.osid.repository.RepositoryException
    {
        return this.repeatable;
    }

    public void updateDisplayName(String displayName)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.shared.Id getId()
    throws org.osid.repository.RepositoryException
    {
        return this.SOURCE_TITLE_PART_STRUCTURE_ID;
    }

    public org.osid.shared.Type getType()
    throws org.osid.repository.RepositoryException
    {
        return this.type;
    }

    public org.osid.repository.RecordStructure getRecordStructure()
    throws org.osid.repository.RepositoryException
    {
        return RecordStructure.getInstance();
    }

    public boolean validatePart(org.osid.repository.Part part)
    throws org.osid.repository.RepositoryException
    {
        return true;
    }

    public org.osid.repository.PartStructureIterator getPartStructures()
    throws org.osid.repository.RepositoryException
    {
        return new PartStructureIterator(new java.util.Vector());
    }
}
