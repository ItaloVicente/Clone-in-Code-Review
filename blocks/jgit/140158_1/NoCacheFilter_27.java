
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static org.eclipse.jgit.http.server.ServletUtils.getRepository;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.internal.storage.file.ObjectDirectory;
import org.eclipse.jgit.lib.Repository;

class IsLocalFilter implements Filter {
	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request
			FilterChain chain) throws IOException
		if (isLocal(getRepository(request)))
			chain.doFilter(request
		else
			((HttpServletResponse) response).sendError(SC_FORBIDDEN);
	}

	private static boolean isLocal(Repository db) {
		return db.getObjectDatabase() instanceof ObjectDirectory;
	}
}
