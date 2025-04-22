
package org.eclipse.jgit.junit.http;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public class MockServletConfig implements ServletConfig {
	private final Map<String

	public void setInitParameter(String name
		parameters.put(name
	}

	@Override
	public String getInitParameter(String name) {
		return parameters.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		final Iterator<String> i = parameters.keySet().iterator();
		return new Enumeration<String>() {
			@Override
			public boolean hasMoreElements() {
				return i.hasNext();
			}

			@Override
			public String nextElement() {
				return i.next();
			}
		};
	}

	@Override
	public String getServletName() {
		return "MOCK_SERVLET";
	}

	@Override
	public ServletContext getServletContext() {
		return null;
	}
}
