
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.eclipse.jgit.http.server.ServletUtils.getRepository;
import static org.eclipse.jgit.http.server.ServletUtils.nocache;
import static org.eclipse.jgit.http.server.ServletUtils.send;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.http.server.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PacketLineOut;
import org.eclipse.jgit.transport.RefAdvertiser.PacketLineOutRefAdvertiser;

abstract class SmartServiceInfoRefs implements Filter {
	private final String svc;

	SmartServiceInfoRefs(final String service) {
		this.svc = service;
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request
			FilterChain chain) throws IOException
		final HttpServletRequest req = (HttpServletRequest) request;

		if (svc.equals(req.getParameter("service"))) {
			final HttpServletResponse rsp = (HttpServletResponse) response;
			nocache(rsp);
			try {
				final Repository db = getRepository(req);
				final ByteArrayOutputStream buf = new ByteArrayOutputStream();
				final PacketLineOut out = new PacketLineOut(buf);
				out.writeString("# service=" + svc + "\n");
				out.end();
				advertise(req
				rsp.setContentType("application/x-" + svc + "-advertisement");
				send(buf.toByteArray()
			} catch (ServiceNotAuthorizedException e) {
				rsp.sendError(SC_UNAUTHORIZED);

			} catch (ServiceNotEnabledException e) {
				rsp.sendError(SC_FORBIDDEN);
			}
		} else {
			chain.doFilter(request
		}
	}

	protected abstract void advertise(HttpServletRequest req
			PacketLineOutRefAdvertiser pck) throws IOException
			ServiceNotEnabledException
}
