
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.eclipse.jgit.http.server.ServletUtils.getRepository;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.http.server.resolver.GetAnyFile;
import org.eclipse.jgit.http.server.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.lib.Repository;

class GetAnyFileFilter implements Filter {
	private final GetAnyFile getAnyFile;

	GetAnyFileFilter(final GetAnyFile getAnyFile) {
		this.getAnyFile = getAnyFile;
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request
			FilterChain chain) throws IOException
		try {
			final Repository db = getRepository(request);
			getAnyFile.access((HttpServletRequest) request
			chain.doFilter(request
		} catch (ServiceNotAuthorizedException e) {
			((HttpServletResponse) response).sendError(SC_UNAUTHORIZED);
		} catch (ServiceNotEnabledException e) {
			((HttpServletResponse) response).sendError(SC_FORBIDDEN);
		}
	}
}
