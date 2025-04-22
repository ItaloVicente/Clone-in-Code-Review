
package org.eclipse.jgit.http.server;

import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

class WrappedRequest extends HttpServletRequestWrapper {
	private final String path;

	private final String pathInfo;

	WrappedRequest(final HttpServletRequest originalRequest
			final Matcher matcher
		super(originalRequest);

		path = computePath(originalRequest
		pathInfo = computePathInfo();
	}

	private static String computePath(final HttpServletRequest originalRequest
			final Matcher matcher
		final String originalServletPath = originalRequest.getServletPath();
		if (pathInfoGroup <= matcher.groupCount()) {
			final int s = matcher.start(pathInfoGroup);
			if (0 <= s)
				return originalServletPath.substring(0
			else
				return originalServletPath;
		} else
			return originalServletPath;
	}

	private String computePathInfo() {
		String r = getRequestURI().substring(getContextPath().length())
				.replaceAll("[/]{2
		if ("".equals(r) && path.length() != 0)
			r = null;
		return r;
	}

	@Override
	public String getPathTranslated() {
		final String p = getPathInfo();
		return p != null ? getRealPath(p) : null;
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
