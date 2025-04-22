
package org.eclipse.jgit.http.server.glue;

import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract class UrlPipeline {
	private final Filter[] filters;

	private final HttpServlet servlet;

	UrlPipeline(Filter[] filters
		this.filters = filters;
		this.servlet = servlet;
	}

	void init(ServletContext context
			throws ServletException {
		for (Filter ref : filters)
			initFilter(ref
		initServlet(servlet
	}

	private static void initFilter(final Filter ref
			final ServletContext context
			throws ServletException {
		if (!inited.contains(ref)) {
			ref.init(new NoParameterFilterConfig(ref.getClass().getName()
					context));
			inited.add(ref);
		}
	}

	private static void initServlet(final HttpServlet ref
			final ServletContext context
			throws ServletException {
		if (!inited.contains(ref)) {
			ref.init(new ServletConfig() {
				@Override
				public String getInitParameter(String name) {
					return null;
				}

				@Override
				public Enumeration<String> getInitParameterNames() {
					return new Enumeration<String>() {
						@Override
						public boolean hasMoreElements() {
							return false;
						}

						@Override
						public String nextElement() {
							throw new NoSuchElementException();
						}
					};
				}

				@Override
				public ServletContext getServletContext() {
					return context;
				}

				@Override
				public String getServletName() {
					return ref.getClass().getName();
				}
			});
			inited.add(ref);
		}
	}

	void destroy(Set<Object> destroyed) {
		for (Filter ref : filters)
			destroyFilter(ref
		destroyServlet(servlet
	}

	private static void destroyFilter(Filter ref
		if (!destroyed.contains(ref)) {
			ref.destroy();
			destroyed.add(ref);
		}
	}

	private static void destroyServlet(HttpServlet ref
		if (!destroyed.contains(ref)) {
			ref.destroy();
			destroyed.add(ref);
		}
	}

	abstract boolean match(HttpServletRequest req);

	void service(HttpServletRequest req
			throws ServletException
		if (0 < filters.length)
			new Chain(filters
		else
			servlet.service(req
	}

	private static class Chain implements FilterChain {
		private final Filter[] filters;

		private final HttpServlet servlet;

		private int filterIdx;

		Chain(Filter[] filters
			this.filters = filters;
			this.servlet = servlet;
		}

		@Override
		public void doFilter(ServletRequest req
				throws IOException
			if (filterIdx < filters.length)
				filters[filterIdx++].doFilter(req
			else
				servlet.service(req
		}
	}
}
