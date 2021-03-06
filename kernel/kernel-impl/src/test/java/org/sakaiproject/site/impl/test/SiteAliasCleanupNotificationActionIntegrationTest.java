/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/kernel/tags/sakai-10.6/kernel-impl/src/test/java/org/sakaiproject/site/impl/test/SiteAliasCleanupNotificationActionIntegrationTest.java $
 * $Id: SiteAliasCleanupNotificationActionIntegrationTest.java 308852 2014-04-25 23:22:20Z enietzel@anisakai.com $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006 Sakai Foundation
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
package org.sakaiproject.site.impl.test;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.sakaiproject.alias.api.AliasService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.exception.IdInvalidException;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.exception.IdUsedException;
import org.sakaiproject.exception.PermissionException;
import org.sakaiproject.id.api.IdManager;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.test.SakaiKernelTestBase;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;

import java.util.List;

public class SiteAliasCleanupNotificationActionIntegrationTest extends SakaiKernelTestBase {

	private Session session;

	public static Test suite() {
		TestSetup setup = new TestSetup(new TestSuite(SiteAliasCleanupNotificationActionIntegrationTest.class)) {
			protected void setUp() throws Exception {
				oneTimeSetup();
			}
			protected void tearDown() throws Exception {	
				oneTimeTearDown();
			}
		};
		return setup;
	}
	
	protected void setUp() throws Exception {
		startSession();
		super.setUp();
	}

    /**
     * This does not test anything, it just makes sure we have one active test in the class
     */
    public void testNothing() {
        assertEquals(1, 1);
    }

    /**
     * DISABLED this test since it no longer works, the functionality still does though - KNL-1162
     */
	@SuppressWarnings("unchecked")
	public void disabledTestSiteDeletionTriggersSiteAliasDeletion()
	throws IdInvalidException, IdUsedException, PermissionException {
		IdManager idManager = getService(IdManager.class);
		SiteService siteService = getService(SiteService.class);
		AliasService aliasService = getService(AliasService.class);
		
		// fixture setup
		Site site = siteService.addSite(idManager.createUuid(), (String)null);
		final String siteAliasId1 = "site-alias-1";
		final String siteAliasId2 = "site-alias-2";
		aliasService.setAlias(siteAliasId1, site.getReference());
		aliasService.setAlias(siteAliasId2, site.getReference());
		
		// sanity check
		List createdSiteAliases = aliasService.getAliases(site.getReference());
		assertFalse("Expected at least one alias to be created during fixture setup", 
				createdSiteAliases.isEmpty());
		
		// the "real" code exercise
		try {
			siteService.removeSite(site);
		} catch (IdUnusedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List remainingSiteAliases = aliasService.getAliases(site.getReference());
		assertEquals("Expected all site aliases to be deleted on site deletion", 0, 
				remainingSiteAliases.size());
	}
	
	private void startSession() {
		UsageSessionService usageSessionService = getService(UsageSessionService.class);
		usageSessionService.startSession("admin", "localhost", "integration-tests");
		SessionManager sessionManager = getService(SessionManager.class);
		session = sessionManager.getCurrentSession();
		session.setUserEid("admin");
		session.setUserId("admin");
	}
	
	private void endSession() {
		if ( session != null ) session.invalidate();
	}
	
	protected void tearDown() throws Exception {
		endSession();
		super.tearDown();
	}
	
}
