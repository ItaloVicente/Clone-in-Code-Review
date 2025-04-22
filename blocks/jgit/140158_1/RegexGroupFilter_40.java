
package org.eclipse.jgit.http.server.glue;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

final class NoParameterFilterConfig implements FilterConfig {
	private final String filterName;

	private final ServletContext context;

	NoParameterFilterConfig(String filterName
		this.filterName = filterName;
		this.context = context;
	}

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
	public String getFilterName() {
		return filterName;
	}
}
