
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

	public String getInitParameter(String name) {
		return null;
	}

	public Enumeration getInitParameterNames() {
		return new Enumeration<String>() {
			public boolean hasMoreElements() {
				return false;
			}

			public String nextElement() {
				throw new NoSuchElementException();
			}
		};
	}

	public ServletContext getServletContext() {
		return context;
	}

	public String getFilterName() {
		return filterName;
	}
}
