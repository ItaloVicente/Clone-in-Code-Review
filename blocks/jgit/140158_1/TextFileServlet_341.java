
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.infoRefsResultType;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.sendError;
import static org.eclipse.jgit.http.server.ServletUtils.ATTRIBUTE_HANDLER;
import static org.eclipse.jgit.http.server.ServletUtils.getRepository;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PacketLineOut;
import org.eclipse.jgit.transport.RefAdvertiser.PacketLineOutRefAdvertiser;
import org.eclipse.jgit.transport.ServiceMayNotContinueException;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

abstract class SmartServiceInfoRefs implements Filter {
	private final String svc;

	private final Filter[] filters;

	SmartServiceInfoRefs(String service
		this.svc = service;
		this.filters = filters.toArray(new Filter[0]);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request
			FilterChain chain) throws IOException
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;

		if (svc.equals(req.getParameter("service"))) {
			final Repository db = getRepository(req);
			try {
				begin(req
			} catch (ServiceNotAuthorizedException e) {
				res.sendError(SC_UNAUTHORIZED
				return;
			} catch (ServiceNotEnabledException e) {
				sendError(req
				return;
			}

			try {
				if (filters.length == 0)
					service(req
				else
					new Chain().doFilter(request
			} finally {
				req.removeAttribute(ATTRIBUTE_HANDLER);
			}
		} else {
			chain.doFilter(request
		}
	}

	private void service(ServletRequest request
			throws IOException {
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;
		final SmartOutputStream buf = new SmartOutputStream(req
		try {
			res.setContentType(infoRefsResultType(svc));

			final PacketLineOut out = new PacketLineOut(buf);
			respond(req
			buf.close();
		} catch (ServiceNotAuthorizedException e) {
			res.sendError(SC_UNAUTHORIZED
		} catch (ServiceNotEnabledException e) {
			sendError(req
		} catch (ServiceMayNotContinueException e) {
			if (e.isOutput())
				buf.close();
			else
				sendError(req
		}
	}

	protected abstract void begin(HttpServletRequest req
			throws IOException
			ServiceNotAuthorizedException;

	protected abstract void advertise(HttpServletRequest req
			PacketLineOutRefAdvertiser pck) throws IOException
			ServiceNotEnabledException

	protected void respond(HttpServletRequest req
			PacketLineOut pckOut
			throws IOException
			ServiceNotAuthorizedException {
		pckOut.end();
		advertise(req
	}

	private class Chain implements FilterChain {
		private int filterIdx;

		@Override
		public void doFilter(ServletRequest req
				throws IOException
			if (filterIdx < filters.length)
				filters[filterIdx++].doFilter(req
			else
				service(req
		}
	}
}
