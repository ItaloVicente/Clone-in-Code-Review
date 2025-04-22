
package org.eclipse.jgit.http.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectDirectory;

class LooseObjectFileServlet extends RepositoryServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		serve(req
	}

	@Override
	protected void doHead(final HttpServletRequest req
			final HttpServletResponse rsp) throws ServletException
		serve(req
	}

	private void serve(final HttpServletRequest req
			final HttpServletResponse rsp
			throws IOException {
		final String etag = req.getPathInfo();
		final Sender send = createSender(req
		if (send != null) {
			try {
				cacheForever(rsp);
				rsp.setHeader("ETag"
				rsp.setContentType("application/octet-stream");
				rsp.setContentLength(send.getContentLength());
				if (sendBody)
					send.sendBody(rsp);
			} finally {
				send.close();
			}
		} else {
			rsp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private Sender createSender(final HttpServletRequest req
			final String looseFileName) {
		final ObjectDatabase db = getRepository(req).getObjectDatabase();
		if (db instanceof ObjectDirectory) {
			final File dir = ((ObjectDirectory) db).getDirectory();
			final File obj = new File(dir
			try {
				return new Sender.FileSender(obj);
			} catch (FileNotFoundException e) {
				return null;
			}

		} else {
			return null;
		}
	}
}
