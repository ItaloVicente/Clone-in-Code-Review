
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.*;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
import static org.eclipse.jgit.http.server.ServletUtils.getInputStream;
import static org.eclipse.jgit.http.server.ServletUtils.getRepository;
import static org.eclipse.jgit.http.server.ServletUtils.nocache;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.http.server.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.http.server.resolver.UploadPackFactory;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UploadPack;
import org.eclipse.jgit.transport.RefAdvertiser.PacketLineOutRefAdvertiser;

class UploadPackServlet extends HttpServlet {
	private static final String REQ_TYPE = "application/x-git-upload-pack-request";

	private static final String RSP_TYPE = "application/x-git-upload-pack-result";

	private static final long serialVersionUID = 1L;

	static class InfoRefs extends SmartServiceInfoRefs {
		private final UploadPackFactory uploadPackFactory;

		InfoRefs(final UploadPackFactory uploadPackFactory) {
			super("git-upload-pack");
			this.uploadPackFactory = uploadPackFactory;
		}

		@Override
		protected void advertise(HttpServletRequest req
				PacketLineOutRefAdvertiser pck) throws IOException
				ServiceNotEnabledException
			uploadPackFactory.create(req
		}
	}

	private final UploadPackFactory uploadPackFactory;

	UploadPackServlet(final UploadPackFactory uploadPackFactory) {
		this.uploadPackFactory = uploadPackFactory;
	}

	@Override
	public void doPost(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		if (!REQ_TYPE.equals(req.getContentType())) {
			rsp.sendError(SC_UNSUPPORTED_MEDIA_TYPE);
			return;
		}

		final Repository db = getRepository(req);
		try {
			final UploadPack up = uploadPackFactory.create(req
			up.setBiDirectionalPipe(false);
			nocache(rsp);
			rsp.setContentType(RSP_TYPE);
			up.upload(getInputStream(req)

		} catch (ServiceNotAuthorizedException e) {
			rsp.reset();
			rsp.sendError(SC_UNAUTHORIZED);
			return;

		} catch (ServiceNotEnabledException e) {
			rsp.reset();
			rsp.sendError(SC_FORBIDDEN);
			return;

		} catch (IOException e) {
			getServletContext().log("Internal error during upload-pack"
			rsp.reset();
			rsp.sendError(SC_INTERNAL_SERVER_ERROR);
			return;
		}
	}
}
