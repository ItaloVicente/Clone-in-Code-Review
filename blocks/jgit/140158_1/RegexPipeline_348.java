
package org.eclipse.jgit.http.server.glue;

import static java.lang.Integer.valueOf;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.eclipse.jgit.http.server.HttpServerText;

public class RegexGroupFilter implements Filter {
	private final int groupIdx;

	public RegexGroupFilter(int groupIdx) {
		if (groupIdx < 1)
			throw new IllegalArgumentException(MessageFormat.format(
					HttpServerText.get().invalidIndex
		this.groupIdx = groupIdx - 1;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(final ServletRequest request
			final ServletResponse rsp
			throws IOException
		final WrappedRequest[] g = groupsFor(request);
		if (groupIdx < g.length)
			chain.doFilter(g[groupIdx]
		else
			throw new ServletException(MessageFormat.format(
					HttpServerText.get().invalidRegexGroup
					valueOf(groupIdx + 1)));
	}

	private static WrappedRequest[] groupsFor(ServletRequest r) {
		return (WrappedRequest[]) r.getAttribute(MetaFilter.REGEX_GROUPS);
	}
}
