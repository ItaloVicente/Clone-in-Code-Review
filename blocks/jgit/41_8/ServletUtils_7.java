
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.eclipse.jgit.http.server.ServletUtils.ATTRIBUTE_REPOSITORY;

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

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.http.server.resolver.RepositoryResolver;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.lib.Repository;

public class RepositoryFilter implements Filter {
	private final RepositoryResolver resolver;

	private ServletContext context;

	public RepositoryFilter(final RepositoryResolver resolver) {
		this.resolver = resolver;
	}

	public void init(final FilterConfig config) throws ServletException {
		context = config.getServletContext();
	}

	public void destroy() {
		context = null;
	}

	public void doFilter(final ServletRequest request
			final ServletResponse rsp
			throws IOException
		if (request.getAttribute(ATTRIBUTE_REPOSITORY) != null) {
			context.log("Internal server error
					+ ATTRIBUTE_REPOSITORY + " was already set when "
					+ getClass().getName() + " was invoked.");
			((HttpServletResponse) rsp).sendError(SC_INTERNAL_SERVER_ERROR);
			return;
		}

		final HttpServletRequest req = (HttpServletRequest) request;

		String name = req.getPathInfo();
		if (name == null || name.length() == 0) {
			((HttpServletResponse) rsp).sendError(SC_NOT_FOUND);
			return;
		}
		if (name.startsWith("/"))
			name = name.substring(1);

		final Repository db;
		try {
			db = resolver.open(req
		} catch (RepositoryNotFoundException e) {
			((HttpServletResponse) rsp).sendError(SC_NOT_FOUND);
			return;
		} catch (ServiceNotEnabledException e) {
			((HttpServletResponse) rsp).sendError(SC_FORBIDDEN);
			return;
		}
		try {
			request.setAttribute(ATTRIBUTE_REPOSITORY
			chain.doFilter(request
		} finally {
			request.removeAttribute(ATTRIBUTE_REPOSITORY);
			db.close();
		}
	}
}
