
package org.eclipse.jgit.http.server;

import static org.eclipse.jgit.util.HttpSupport.HDR_ETAG;
import static org.eclipse.jgit.util.HttpSupport.HDR_IF_MODIFIED_SINCE;
import static org.eclipse.jgit.util.HttpSupport.HDR_IF_NONE_MATCH;
import static org.eclipse.jgit.util.HttpSupport.HDR_LAST_MODIFIED;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.ObjectDirectory;
import org.eclipse.jgit.lib.Repository;
import static javax.servlet.http.HttpServletResponse.*;
import static org.eclipse.jgit.http.server.ServletUtils.*;

abstract class ObjectFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static class Loose extends ObjectFileServlet {
		private static final long serialVersionUID = 1L;

		Loose() {
			super("application/x-git-loose-object");
		}

		@Override
		String etag(final FileSender sender) throws IOException {
			return Long.toHexString(sender.getLastModified());
		}
	}

	private static abstract class PackData extends ObjectFileServlet {
		private static final long serialVersionUID = 1L;

		PackData(String contentType) {
			super(contentType);
		}

		@Override
		String etag(final FileSender sender) throws IOException {
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

	abstract String etag(FileSender sender) throws IOException;

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
		final File obj = new File(objects(req)
		final FileSender sender;
		try {
			sender = new FileSender(obj);
		} catch (FileNotFoundException e) {
			nocache(rsp);
			rsp.sendError(SC_NOT_FOUND);
			return;
		}

		try {
			final String etag = etag(sender);
			final long lastModified = sender.getLastModified() / 1000 * 1000;

			String ifNoneMatch = req.getHeader(HDR_IF_NONE_MATCH);
			if (etag != null && etag.equals(ifNoneMatch)) {
				rsp.sendError(SC_NOT_MODIFIED);
				return;
			}

			long ifModifiedSince = req.getDateHeader(HDR_IF_MODIFIED_SINCE);
			if (0 < lastModified && lastModified < ifModifiedSince) {
				rsp.sendError(SC_NOT_MODIFIED);
				return;
			}

			cacheForever(rsp);
			if (etag != null)
				rsp.setHeader(HDR_ETAG
			if (0 < lastModified)
				rsp.setDateHeader(HDR_LAST_MODIFIED
			rsp.setContentType(contentType);
			sender.serve(req
		} finally {
			sender.close();
		}
	}

	private static File objects(final HttpServletRequest req) {
		final Repository db = getRepository(req);
		return ((ObjectDirectory) db.getObjectDatabase()).getDirectory();
	}
}
