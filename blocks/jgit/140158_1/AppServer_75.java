
package org.eclipse.jgit.junit.http;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;

public class AccessEvent {
	private final String method;

	private final String uri;

	private final Map<String

	private final Map<String

	private final int status;

	private final Map<String

	AccessEvent(Request req
		method = req.getMethod();
		uri = req.getRequestURI();
		requestHeaders = cloneHeaders(req);
		parameters = clone(req.getParameterMap());

		status = rsp.getStatus();
		responseHeaders = cloneHeaders(rsp);
	}

	private static Map<String
		Map<String
		Enumeration hn = req.getHeaderNames();
		while (hn.hasMoreElements()) {
			String key = (String) hn.nextElement();
			if (!r.containsKey(key)) {
				r.put(key
			}
		}
		return Collections.unmodifiableMap(r);
	}

	private static Map<String
		Map<String
		Enumeration<String> hn = rsp.getHttpFields().getFieldNames();
		while (hn.hasMoreElements()) {
			String key = hn.nextElement();
			if (!r.containsKey(key)) {
				Enumeration<String> v = rsp.getHttpFields().getValues(key);
				r.put(key
			}
		}
		return Collections.unmodifiableMap(r);
	}

	@SuppressWarnings("unchecked")
	private static Map<String
		return new TreeMap<>(parameterMap);
	}

	public String getMethod() {
		return method;
	}

	public String getPath() {
		return uri;
	}

	public String getRequestHeader(String name) {
		return requestHeaders.get(name);
	}

	public String getParameter(String name) {
		String[] r = parameters.get(name);
		return r != null && 1 <= r.length ? r[0] : null;
	}

	public Map<String
		return parameters;
	}

	public int getStatus() {
		return status;
	}

	public String getResponseHeader(String name) {
		return responseHeaders.get(name);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(method);
		b.append(' ');
		b.append(uri);
		if (!parameters.isEmpty()) {
			b.append('?');
			boolean first = true;
			for (Map.Entry<String
				for (String val : e.getValue()) {
					if (!first) {
						b.append('&');
					}
					first = false;

					b.append(e.getKey());
					b.append('=');
					b.append(val);
				}
			}
		}
		b.append(' ');
		b.append(status);
		return b.toString();
	}
}
