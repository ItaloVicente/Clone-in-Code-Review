
package org.eclipse.jgit.http.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public abstract class RepositoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String ATTRIBUTE_REPOSITORY = "org.eclipse.jgit.Repository";

	public static Repository getRepository(final HttpServletRequest req) {
		Repository db = (Repository) req.getAttribute(ATTRIBUTE_REPOSITORY);
		if (db == null)
			throw new IllegalStateException("Expected Repository attribute");
		return db;
	}

	static void setRepository(final HttpServletRequest req
		assert db != null;
		req.setAttribute(ATTRIBUTE_REPOSITORY
	}

	protected void nocache(final HttpServletResponse rsp) {
		rsp.setHeader("Expires"
		rsp.setHeader("Pragma"
		rsp.setHeader("Cache-Control"
	}

	protected void cacheForever(final HttpServletResponse rsp) {
		final long now = System.currentTimeMillis();
		rsp.setHeader("Cache-Control"
		rsp.setDateHeader("Expires"
		rsp.setDateHeader("Date"
	}

	protected void sendPlainText(final String content
			final HttpServletRequest req
			throws IOException {
		final String enc = Constants.CHARACTER_ENCODING;
		final byte[] raw = content.getBytes(enc);
		rsp.setContentType("text/plain");
		rsp.setCharacterEncoding(enc);
		send(raw
	}

	protected void send(byte[] content
			final HttpServletResponse rsp)
			throws IOException {
		content = sendInit(content
		final OutputStream out = rsp.getOutputStream();
		try {
			out.write(content);
		} finally {
			out.close();
		}
	}

	private byte[] sendInit(byte[] content
			final HttpServletResponse rsp) throws IOException {
		rsp.setHeader("ETag"
		if (256 < content.length && acceptsGzipEncoding(req)) {
			content = compress(content);
			rsp.setHeader("Content-Encoding"
		}
		rsp.setContentLength(content.length);
		return content;
	}

	private static boolean acceptsGzipEncoding(final HttpServletRequest req) {
		final String accepts = req.getHeader("Accept-Encoding");
		return accepts != null && 0 <= accepts.indexOf("gzip");
	}

	private static byte[] compress(final byte[] raw) throws IOException {
		final int maxLen = raw.length + 32;
		final ByteArrayOutputStream out = new ByteArrayOutputStream(maxLen);
		final GZIPOutputStream gz = new GZIPOutputStream(out);
		gz.write(raw);
		gz.finish();
		gz.flush();
		return out.toByteArray();
	}

	private static String etag(final byte[] content) {
		final MessageDigest md = Constants.newMessageDigest();
		md.update(content);
		return ObjectId.fromRaw(md.digest()).getName();
	}
}
