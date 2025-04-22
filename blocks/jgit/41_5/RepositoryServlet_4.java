
package org.eclipse.jgit.http.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.http.server.resolver.FileResolver;
import org.eclipse.jgit.http.server.resolver.RepositoryResolver;

public class RepositoryRouter implements Filter {
	private RepositoryResolver resolver;

	private ServletContext context;

	private Collection<ServletDefinition> servlets;

	public RepositoryRouter() {
		this.resolver = null;
	}

	public RepositoryRouter(final RepositoryResolver resolver) {
		if (resolver == null)
			throw new NullPointerException("RepositoryResolver not supplied");
		this.resolver = resolver;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		if (resolver == null) {
			final String basePath = filterConfig.getInitParameter("base-path");
			if (basePath == null || "".equals(basePath))
				throw new ServletException("Filter parameter base-path not set");
			resolver = new FileResolver(new File(basePath));
		}

		context = filterConfig.getServletContext();
		servlets = new ArrayList<ServletDefinition>();

		bind("^/(.*)/(HEAD|refs/.*)$"
		bind("^/(.*)/info/refs$"
		bind("^/(.*)/objects/info/packs$"
		bind("^/(.*)/objects/([0-9a-f]{2}/[0-9a-f]{38})$"
				new ObjectFileServlet.Loose());
		bind("^/(.*)/objects/(pack/pack-[0-9a-f]{40}\\.pack)$"
				new ObjectFileServlet.Pack());
		bind("^/(.*)/objects/(pack/pack-[0-9a-f]{40}\\.idx)$"
				new ObjectFileServlet.PackIdx());

		for (ServletDefinition d : servlets)
			d.init(context);
	}

	private void bind(final String pattern
		servlets.add(new ServletDefinition(pattern
	}

	public void destroy() {
		for (ServletDefinition d : servlets)
			d.destroy();
	}

	public void doFilter(final ServletRequest request
			final ServletResponse response
			throws IOException
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse rsp = (HttpServletResponse) response;

		for (ServletDefinition d : servlets) {
			if (d.canService(req)) {
				d.service(req
				return;
			}
		}

		chain.doFilter(req
	}
}
