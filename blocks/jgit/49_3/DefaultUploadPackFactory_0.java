
package org.eclipse.jgit.http.server;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.http.server.resolver.UploadPackFactory;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UploadPack;

class UploadPackServlet extends RepositoryServlet {
	private static final String REQ_TYPE = "application/x-git-upload-pack-input";

	private static final String RSP_TYPE = "application/x-git-upload-pack-data";

	private static final long serialVersionUID = 1L;

	private final UploadPackFactory uploadPackFactory;

	UploadPackServlet(final UploadPackFactory uploadPackFactory) {
		this.uploadPackFactory = uploadPackFactory;
	}

	@Override
	public void doPost(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		if (!REQ_TYPE.equals(req.getContentType())) {
			rsp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
			return;
		}

		final Repository db = getRepository(req);
		try {
			final UploadPack up = uploadPackFactory.create(req
			up.setBiDirectionalPipe(false);
			rsp.setContentType(RSP_TYPE);
			up.upload(req.getInputStream()

		} catch (ServiceNotEnabledException e) {
			rsp.reset();
			rsp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;

		} catch (IOException e) {
			getServletContext().log("Internal error during upload-pack"
			rsp.reset();
			rsp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
	}
}
