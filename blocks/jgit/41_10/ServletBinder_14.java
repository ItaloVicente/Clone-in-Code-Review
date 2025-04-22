
package org.eclipse.jgit.http.server.glue;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.eclipse.jgit.http.server.glue.MetaServlet.REGEX_GROUPS;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class RegexPipeline extends UrlPipeline {
	static class Binder extends ServletBinderImpl {
		private final Pattern pattern;

		Binder(final String p) {
			pattern = Pattern.compile(p);
		}

		UrlPipeline create() {
			return new RegexPipeline(pattern
		}
	}

	private final Pattern pattern;

	RegexPipeline(final Pattern pattern
			final HttpServlet servlet) {
		super(filters
		this.pattern = pattern;
	}

	boolean match(final HttpServletRequest req) {
		final String pathInfo = req.getPathInfo();
		return pathInfo != null && pattern.matcher(pathInfo).matches();
	}

	@Override
	void service(HttpServletRequest req
			throws ServletException
		final String reqInfo = req.getPathInfo();
		if (reqInfo == null) {
			rsp.sendError(SC_NOT_FOUND);
			return;
		}

		final Matcher cur = pattern.matcher(reqInfo);
		if (!cur.matches()) {
			rsp.sendError(SC_NOT_FOUND);
			return;
		}

		final String reqPath = req.getServletPath();
		final Object old = req.getAttribute(REGEX_GROUPS);
		try {
			if (1 <= cur.groupCount()) {
				WrappedRequest groups[] = new WrappedRequest[cur.groupCount()];
				for (int groupId = 1; groupId <= cur.groupCount(); groupId++) {
					final int s = cur.start(groupId);
					final String path

					path = reqPath + reqInfo.substring(0
					info = cur.group(groupId);
					groups[groupId - 1] = new WrappedRequest(req
				}
				req.setAttribute(REGEX_GROUPS
				super.service(groups[0]

			} else {
				final String path = reqPath + reqInfo;
				final String info = null;
				super.service(new WrappedRequest(req
			}
		} finally {
			if (old != null)
				req.setAttribute(REGEX_GROUPS
			else
				req.removeAttribute(REGEX_GROUPS);
		}
	}

	@Override
	public String toString() {
		return "Pipeline[regex: " + pattern + " ]";
	}
}
