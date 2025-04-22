
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

import org.eclipse.jgit.http.server.resolver.AsIsFileService;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

class AsIsFileFilter implements Filter {
	private final AsIsFileService asIs;

	AsIsFileFilter(AsIsFileService getAnyFile) {
		this.asIs = getAnyFile;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request
			FilterChain chain) throws IOException
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			final Repository db = getRepository(request);
			asIs.access(req
			chain.doFilter(request
		} catch (ServiceNotAuthorizedException e) {
			res.sendError(SC_UNAUTHORIZED
		} catch (ServiceNotEnabledException e) {
			res.sendError(SC_FORBIDDEN
		}
	}
}
