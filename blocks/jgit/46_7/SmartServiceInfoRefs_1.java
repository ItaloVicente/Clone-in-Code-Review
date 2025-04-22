
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static javax.servlet.http.HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
import static org.eclipse.jgit.http.server.ServletUtils.getInputStream;
import static org.eclipse.jgit.http.server.ServletUtils.getRepository;
import static org.eclipse.jgit.http.server.ServletUtils.nocache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.http.server.resolver.ReceivePackFactory;
import org.eclipse.jgit.http.server.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.RefAdvertiser.PacketLineOutRefAdvertiser;

class ReceivePackServlet extends HttpServlet {
	private static final String REQ_TYPE = "application/x-git-receive-pack-request";

	private static final String RSP_TYPE = "application/x-git-receive-pack-result";

	private static final long serialVersionUID = 1L;

	static class InfoRefs extends SmartServiceInfoRefs {
		private final ReceivePackFactory receivePackFactory;

		InfoRefs(final ReceivePackFactory receivePackFactory) {
			super("git-receive-pack");
			this.receivePackFactory = receivePackFactory;
		}

		@Override
		protected void advertise(HttpServletRequest req
				PacketLineOutRefAdvertiser pck) throws IOException
				ServiceNotEnabledException
			receivePackFactory.create(req
		}
	}

	private final ReceivePackFactory receivePackFactory;

	ReceivePackServlet(final ReceivePackFactory receivePackFactory) {
		this.receivePackFactory = receivePackFactory;
	}

	@Override
	public void doPost(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		if (!REQ_TYPE.equals(req.getContentType())) {
			rsp.sendError(SC_UNSUPPORTED_MEDIA_TYPE);
			return;
		}

		final Repository db = getRepository(req);
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			final ReceivePack rp = receivePackFactory.create(req
			rp.setBiDirectionalPipe(false);
			rp.receive(getInputStream(req)

		} catch (ServiceNotAuthorizedException e) {
			rsp.sendError(SC_UNAUTHORIZED);
			return;

		} catch (ServiceNotEnabledException e) {
			rsp.sendError(SC_FORBIDDEN);
			return;

		} catch (IOException e) {
			getServletContext().log("Internal error during receive-pack"
			rsp.sendError(SC_INTERNAL_SERVER_ERROR);
			return;
		}

		reply(rsp
	}

	private void reply(final HttpServletResponse rsp
			throws IOException {
		nocache(rsp);
		rsp.setContentType(RSP_TYPE);
		rsp.setContentLength(result.length);
		final OutputStream os = rsp.getOutputStream();
		try {
			os.write(result);
			os.flush();
		} finally {
			os.close();
		}
	}
}
