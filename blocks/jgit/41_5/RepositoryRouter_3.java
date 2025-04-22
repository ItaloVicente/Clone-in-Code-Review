
package org.eclipse.jgit.http.server;

import static org.eclipse.jgit.util.HttpSupport.HDR_ETAG;
import static org.eclipse.jgit.util.HttpSupport.HDR_IF_MODIFIED_SINCE;
import static org.eclipse.jgit.util.HttpSupport.HDR_IF_NONE_MATCH;
import static org.eclipse.jgit.util.HttpSupport.HDR_LAST_MODIFIED;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectDirectory;

abstract class ObjectFileServlet extends RepositoryServlet {
	private static final long serialVersionUID = 1L;

	static class Loose extends ObjectFileServlet {
		private static final long serialVersionUID = 1L;

		Loose() {
			super("application/x-git-loose-object");
		}

		@Override
		String etag(final Sender.FileSender sender) throws IOException {
			return Long.toHexString(sender.getLastModified());
		}
	}

	private static abstract class PackData extends ObjectFileServlet {
		private static final long serialVersionUID = 1L;

		PackData(String contentType) {
			super(contentType);
		}

		@Override
		String etag(final Sender.FileSender sender) throws IOException {
			return sender.getTailChecksum();
		}
	}

	static class Pack extends PackData {
		private static final long serialVersionUID = 1L;

		Pack() {
			super("application/x-git-packed-objects");
		}
	}

	static class PackIdx extends PackData {
		private static final long serialVersionUID = 1L;

		PackIdx() {
			super("application/x-git-packed-objects-toc");
		}
	}

	private final String contentType;

	ObjectFileServlet(final String contentType) {
		this.contentType = contentType;
	}

	abstract String etag(Sender.FileSender sender) throws IOException;

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
		final String fileName = req.getPathInfo();
		final Sender.FileSender sender = createSender(req
		if (sender == null) {
			nocache(rsp);
			rsp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		try {
			final String etag = etag(sender);
			final long lastModified = sender.getLastModified() / 1000 * 1000;

			String ifNoneMatch = req.getHeader(HDR_IF_NONE_MATCH);
			if (etag.equals(ifNoneMatch)) {
				rsp.sendError(HttpServletResponse.SC_NOT_MODIFIED);
				return;
			}

			long ifModifiedSince = req.getDateHeader(HDR_IF_MODIFIED_SINCE);
			if (lastModified < ifModifiedSince) {
				rsp.sendError(HttpServletResponse.SC_NOT_MODIFIED);
				return;
			}

			cacheForever(rsp);
			rsp.setHeader(HDR_ETAG
			rsp.setDateHeader(HDR_LAST_MODIFIED
			rsp.setContentType(contentType);
			rsp.setContentLength(sender.getContentLength());
			if (sendBody)
				sender.sendBody(rsp);
		} finally {
			sender.close();
		}
	}

	private Sender.FileSender createSender(final HttpServletRequest req
			final String fileName) {
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
