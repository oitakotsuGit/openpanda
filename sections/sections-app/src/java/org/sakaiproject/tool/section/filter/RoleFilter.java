/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sections/tags/sakai-10.6/sections-app/src/java/org/sakaiproject/tool/section/filter/RoleFilter.java $
 * $Id: RoleFilter.java 105080 2012-02-24 23:10:31Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2005, 2006, 2008 The Sakai Foundation
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
package org.sakaiproject.tool.section.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.section.api.facade.manager.Authn;
import org.sakaiproject.section.api.facade.manager.Authz;
import org.sakaiproject.section.api.facade.manager.Context;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import org.sakaiproject.component.cover.ServerConfigurationService;

/**
 * An authorization filter to keep users out of pages they are not authorized
 * to access.
 *  
 * @author <a href="mailto:jholtzman@berkeley.edu">Josh Holtzman</a>
 *
 */
public class RoleFilter implements Filter {
	private static Log logger = LogFactory.getLog(RoleFilter.class);

	private String authnBeanName;
	private String authzBeanName;
	private String contextBeanName;
	private String authorizationFilterConfigurationBeanName;
	private String selectSiteRedirect;

    private ApplicationContext ac;

	public void init(FilterConfig filterConfig) throws ServletException {
        if(logger.isInfoEnabled()) logger.info("Initializing sections role filter");

        ac = (ApplicationContext)filterConfig.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        authnBeanName = filterConfig.getInitParameter("authnServiceBean");
		authzBeanName = filterConfig.getInitParameter("authzServiceBean");
		contextBeanName = filterConfig.getInitParameter("contextManagementServiceBean");
		authorizationFilterConfigurationBeanName = filterConfig.getInitParameter("authorizationFilterConfigurationBean");
		selectSiteRedirect = filterConfig.getInitParameter("selectSiteRedirect");
    }

	public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)servletRequest;
		String servletPath = request.getServletPath();
		if (logger.isDebugEnabled()) logger.debug("Filtering request for servletPath=" + servletPath);
		servletPath = servletPath.replaceFirst("^/", "");
		if (servletPath.indexOf("/") >= 0) {
			// Only protect the top-level folder, to allow for login through
			// a subdirectory, shared resource files, and so on.
			chain.doFilter(request, response);
			return;
		}

		Authn authn = (Authn)ac.getBean(authnBeanName);
		Authz authz = (Authz)ac.getBean(authzBeanName);
		Context context = (Context)ac.getBean(contextBeanName);
		AuthorizationFilterConfigurationBean authzFilterConfigBean = (AuthorizationFilterConfigurationBean)ac.getBean(authorizationFilterConfigurationBeanName);
		String userUid = authn.getUserUid(request);

        if (logger.isDebugEnabled()) logger.debug("Filtering request for user " + userUid + ", pathInfo=" + request.getPathInfo());

		// Try to get the currently selected site context, if any
		String siteContext = context.getContext(request);
		
        if(logger.isDebugEnabled()) logger.debug("context=" + siteContext);

		if (siteContext != null) {
			// Get the name of the page from the servlet path.
			String[] splitPath = servletPath.split("[./]");
			String pageName = splitPath[0];

			boolean isAuthorized = false;
			if(authz.isSectionManagementAllowed(userUid, siteContext) &&
					authzFilterConfigBean.getManageAllSections().contains(pageName)) {
				isAuthorized = true;
			} else if (authz.isViewAllSectionsAllowed(userUid, siteContext)
					&& authzFilterConfigBean.getViewAllSections().contains(pageName)) {
				isAuthorized = true;
			} else if (authz.isSectionTaManagementAllowed(userUid, siteContext)
					&& authzFilterConfigBean.getManageTeachingAssistants().contains(pageName)) {
				isAuthorized = true;
			} else if (authz.isSectionEnrollmentMangementAllowed(userUid, siteContext)
					&& authzFilterConfigBean.getManageEnrollments().contains(pageName)) {
				isAuthorized = true;
			} else if (authz.isViewOwnSectionsAllowed(userUid, siteContext)
					&& authzFilterConfigBean.getViewOwnSections().contains(pageName)) {
				isAuthorized = true;
			} else if (pageName.contains("closed")) {
				isAuthorized = true;
			}

			// SAK-13408 - This fix addresses the problem of the filter receiving a blank field on WebSphere.
			// Without this, users would be denied access to the tool
			if ( "websphere".equals(ServerConfigurationService.getString("servlet.container")) && ( isAuthorized || pageName.equals(""))) {
				chain.doFilter(request,response);
			} else if ( !"websphere".equals(ServerConfigurationService.getString("servlet.container")) && isAuthorized ) {
				chain.doFilter(request, response);
			} else {
				logger.error("AUTHORIZATION FAILURE: User " + userUid + " in site " +
					siteContext + " attempted to reach URL " + request.getRequestURL());
				((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} else {
			if (selectSiteRedirect != null) {
				((HttpServletResponse)response).sendRedirect(selectSiteRedirect);
			} else {
				((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
	}

	public void destroy() {
		ac = null;
	}
}
