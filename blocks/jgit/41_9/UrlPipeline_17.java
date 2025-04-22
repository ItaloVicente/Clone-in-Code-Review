
package org.eclipse.jgit.http.server.glue;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class SuffixPipeline extends UrlPipeline {
	static class Binder extends ServletBinderImpl {
		private final String suffix;

		Binder(final String suffix) {
			this.suffix = suffix;
		}

		UrlPipeline create() {
			return new SuffixPipeline(suffix
		}
	}

	private final String suffix;

	private final int suffixLen;

	SuffixPipeline(final String suffix
			final HttpServlet servlet) {
		super(filters
		this.suffix = suffix;
		this.suffixLen = suffix.length();
	}

	boolean match(final HttpServletRequest req) {
		final String pathInfo = req.getPathInfo();
		return pathInfo != null && pathInfo.endsWith(suffix);
	}

	@Override
	void service(HttpServletRequest req
			throws ServletException
		String curInfo = req.getPathInfo();
		String newPath = req.getServletPath() + curInfo;
		String newInfo = curInfo.substring(0
		super.service(new WrappedRequest(req
	}

	@Override
	public String toString() {
		return "Pipeline[ *" + suffix + " ]";
	}
}
