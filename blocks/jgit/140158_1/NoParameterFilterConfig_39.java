
package org.eclipse.jgit.http.server.glue;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MetaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final MetaFilter filter;

	public MetaServlet() {
		this(new MetaFilter());
	}

	protected MetaServlet(MetaFilter delegateFilter) {
		filter = delegateFilter;
	}

	protected MetaFilter getDelegateFilter() {
		return filter;
	}

	public ServletBinder serve(String path) {
		return filter.serve(path);
	}

	public ServletBinder serveRegex(String expression) {
		return filter.serveRegex(expression);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		String name = filter.getClass().getName();
		ServletContext ctx = config.getServletContext();
		filter.init(new NoParameterFilterConfig(name
	}

	@Override
	public void destroy() {
		filter.destroy();
	}

	@Override
	protected void service(HttpServletRequest req
			throws ServletException
		filter.doFilter(req
			@Override
			public void doFilter(ServletRequest request
					ServletResponse response) throws IOException
					ServletException {
				((HttpServletResponse) response).sendError(SC_NOT_FOUND);
			}
		});
	}

	protected ServletBinder register(ServletBinder b) {
		return filter.register(b);
	}
}
