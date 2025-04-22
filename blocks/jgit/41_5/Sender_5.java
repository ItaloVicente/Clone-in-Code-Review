
package org.eclipse.jgit.http.server;

import static org.eclipse.jgit.util.HttpSupport.ENCODING_GZIP;
import static org.eclipse.jgit.util.HttpSupport.HDR_CACHE_CONTROL;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_ENCODING;
import static org.eclipse.jgit.util.HttpSupport.HDR_DATE;
import static org.eclipse.jgit.util.HttpSupport.HDR_ETAG;
import static org.eclipse.jgit.util.HttpSupport.HDR_EXPIRES;
import static org.eclipse.jgit.util.HttpSupport.HDR_PRAGMA;

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
		rsp.setHeader(HDR_EXPIRES
		rsp.setHeader(HDR_PRAGMA
		rsp.setHeader(HDR_CACHE_CONTROL
	}

	protected void cacheForever(final HttpServletResponse rsp) {
		final long now = System.currentTimeMillis();
		rsp.setDateHeader(HDR_DATE
		rsp.setDateHeader(HDR_EXPIRES
		rsp.setHeader(HDR_CACHE_CONTROL
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
			out.flush();
		} finally {
			out.close();
		}
	}

	private byte[] sendInit(byte[] content
			final HttpServletResponse rsp) throws IOException {
		rsp.setHeader(HDR_ETAG
		if (256 < content.length && acceptsGzipEncoding(req)) {
			content = compress(content);
			rsp.setHeader(HDR_CONTENT_ENCODING
		}
		rsp.setContentLength(content.length);
		return content;
	}

	private static boolean acceptsGzipEncoding(final HttpServletRequest req) {
		final String accepts = req.getHeader(HDR_CONTENT_ENCODING);
		return accepts != null && 0 <= accepts.indexOf(ENCODING_GZIP);
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
