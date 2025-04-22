
package org.eclipse.jgit.http.server;

import static org.eclipse.jgit.util.HttpSupport.HDR_CACHE_CONTROL;
import static org.eclipse.jgit.util.HttpSupport.HDR_EXPIRES;
import static org.eclipse.jgit.util.HttpSupport.HDR_PRAGMA;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

class NoCacheFilter implements Filter {
	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request
			FilterChain chain) throws IOException
		HttpServletResponse rsp = (HttpServletResponse) response;

		rsp.setHeader(HDR_EXPIRES
		rsp.setHeader(HDR_PRAGMA

		final String nocache = "no-cache
		rsp.setHeader(HDR_CACHE_CONTROL

		chain.doFilter(request
	}
}
