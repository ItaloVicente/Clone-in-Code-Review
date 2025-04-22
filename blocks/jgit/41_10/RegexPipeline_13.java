
package org.eclipse.jgit.http.server.glue;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RegexGroupFilter implements Filter {
	private final int groupIdx;

	public RegexGroupFilter(final int groupIdx) {
		if (groupIdx < 1)
			throw new IllegalArgumentException("Invalid index: " + groupIdx);
		this.groupIdx = groupIdx - 1;
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(final ServletRequest request
			final ServletResponse rsp
			throws IOException
		final WrappedRequest[] g = groupsFor(request);
		if (groupIdx < g.length)
			chain.doFilter(g[groupIdx]
		else
			throw new ServletException("Invalid regex group " + (groupIdx + 1));
	}

	private static WrappedRequest[] groupsFor(final ServletRequest r) {
		return (WrappedRequest[]) r.getAttribute(MetaServlet.REGEX_GROUPS);
	}
}
