
package org.eclipse.jgit.http.server.glue;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.http.server.HttpServerText;

abstract class ServletBinderImpl implements ServletBinder {
	private final List<Filter> filters;

	private HttpServlet httpServlet;

	ServletBinderImpl() {
		this.filters = new ArrayList<>();
	}

	@Override
	public ServletBinder through(Filter filter) {
		if (filter == null)
			throw new NullPointerException(HttpServerText.get().filterMustNotBeNull);
		filters.add(filter);
		return this;
	}

	@Override
	public void with(HttpServlet servlet) {
		if (servlet == null)
			throw new NullPointerException(HttpServerText.get().servletMustNotBeNull);
		if (httpServlet != null)
			throw new IllegalStateException(HttpServerText.get().servletWasAlreadyBound);
		httpServlet = servlet;
	}

	protected HttpServlet getServlet() {
		if (httpServlet != null)
			return httpServlet;
		else
			return new ErrorServlet(HttpServletResponse.SC_NOT_FOUND);
	}

	protected Filter[] getFilters() {
		return filters.toArray(new Filter[0]);
	}

	abstract UrlPipeline create();
}
