
package org.eclipse.jgit.http.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ReceivePack;

class ReceivePackServlet extends RepositoryServlet {
	private static final String REQ_TYPE = "application/x-git-receive-pack-input";

	private static final String RSP_TYPE = "application/x-git-receive-pack-status";

	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		if (!REQ_TYPE.equals(req.getContentType())) {
			rsp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
			return;
		}

		final Repository db = getRepository(req);
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			final ReceivePack rp = new ReceivePack(db);
			rp.setBiDirectionalPipe(false);
			rp.receive(req.getInputStream()
		} catch (IOException e) {
			getServletContext().log("Internal error during receive-pack"
			rsp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
		} finally {
			os.close();
		}
	}
}
