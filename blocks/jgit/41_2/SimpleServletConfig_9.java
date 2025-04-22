
package org.eclipse.jgit.http.server;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

class SimpleServletConfig implements ServletConfig {
	private final String servletName;

	private final ServletContext context;

	SimpleServletConfig(final String name
		this.servletName = name;
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

	public String getServletName() {
		return servletName;
	}
}
