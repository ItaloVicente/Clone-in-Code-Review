
package org.eclipse.jgit.http.server.glue;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

abstract class ServletBinderImpl implements ServletBinder {
	private final List<Filter> filters;

	private HttpServlet httpServlet;

	ServletBinderImpl() {
		this.filters = new ArrayList<Filter>();
	}

	public ServletBinder through(Filter filter) {
		if (filter == null)
			throw new NullPointerException("filter must not be null");
		filters.add(filter);
		return this;
	}

	public void with(HttpServlet servlet) {
		if (servlet == null)
			throw new NullPointerException("servlet must not be null");
		if (httpServlet != null)
			throw new IllegalStateException("servlet was already bound");
		httpServlet = servlet;
	}

	protected HttpServlet getServlet() {
		if (httpServlet != null)
			return httpServlet;
		else
			return new ErrorServlet(HttpServletResponse.SC_NOT_FOUND);
	}

	protected Filter[] getFilters() {
		return filters.toArray(new Filter[filters.size()]);
	}

	abstract UrlPipeline create();
}
