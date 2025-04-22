
package org.eclipse.jgit.http.test.util;

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

	public String getInitParameter(String name) {
		return parameters.get(name);
	}

	public Enumeration getInitParameterNames() {
		final Iterator<String> i = parameters.keySet().iterator();
		return new Enumeration<String>() {
			public boolean hasMoreElements() {
				return i.hasNext();
			}

			public String nextElement() {
				return i.next();
			}
		};
	}

	public String getServletName() {
		return "MOCK_SERVLET";
	}

	public ServletContext getServletContext() {
		return null;
	}
}
