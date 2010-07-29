/**
 * 
 */
package org.pidster.tomcat.utils.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * 
 * @author pid
 */
public class UserAgentFilter implements Filter {
	
	private FilterConfig config;
	
	private ServletContext context;
	
	private String redirectPage;
	
	private String[] denies = new String[] {};

	private String[] allows = new String[] {};

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hres = (HttpServletResponse) res;
		
		String userAgent = hreq.getHeader("User-agent");
		
		// check we have actually got a User-agent header
		if (userAgent == null || userAgent.isEmpty()) {
			// auto-fail because we can't test without a User-agent		
		}

		if (denies.length > 0) {
			for (String deny : denies) {
				if (userAgent.matches(deny)) {
					// fail if the regex matches
					fail(hreq, hres);
					return;
				}
			}
			
			chain.doFilter(hreq, hres);
		}
		
		if (allows.length > 0) {
			for (String allow : allows) {
				if (userAgent.matches(allow)) {
					// pass if the regex matches
					chain.doFilter(hreq, hres);
					return;
				}
			}
			
			// fail if we reach here
			fail(hreq, hres);
		}
	}
	
	/**
	 * @param hreq
	 * @param hres
	 * @throws IOException 
	 */
	private void fail(HttpServletRequest hreq, HttpServletResponse hres) throws IOException {
		// 
		if (redirectPage == null || redirectPage.isEmpty()) {
			hres.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		hres.sendRedirect(hres.encodeRedirectURL(this.redirectPage));
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		this.context = config.getServletContext();
		
		if (config.getInitParameter("redirectPage") != null) {
			this.redirectPage = config.getInitParameter("redirectPage");
		}
		
		if (config.getInitParameter("denies") != null) {
			this.denies = config.getInitParameter("denies").split("\\,");
		}
		
		if (config.getInitParameter("allows") != null) {
			this.allows = config.getInitParameter("allows").split("\\,");
		}
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// 
	}


}
