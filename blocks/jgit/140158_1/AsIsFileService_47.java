
package org.eclipse.jgit.http.server.glue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class WrappedRequest extends HttpServletRequestWrapper {
	private final String path;

	private final String pathInfo;

	public WrappedRequest(final HttpServletRequest originalRequest
			final String path
		super(originalRequest);
		this.path = path;
		this.pathInfo = pathInfo;
	}

	@Override
	public String getPathTranslated() {
		final String p = getPathInfo();
		return p != null ? getSession().getServletContext().getRealPath(p) : null;
	}

	@Override
	public String getPathInfo() {
		return pathInfo;
	}

	@Override
	public String getServletPath() {
		return path;
	}
}
