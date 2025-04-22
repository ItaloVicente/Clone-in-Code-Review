
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
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public final class ServletUtils {
	public static final String ATTRIBUTE_REPOSITORY = "org.eclipse.jgit.Repository";

	public static Repository getRepository(final ServletRequest req) {
		Repository db = (Repository) req.getAttribute(ATTRIBUTE_REPOSITORY);
		if (db == null)
			throw new IllegalStateException("Expected Repository attribute");
		return db;
	}

	public static InputStream getInputStream(final HttpServletRequest req)
			throws IOException {
		InputStream in = req.getInputStream();
		final String enc = req.getHeader(HDR_CONTENT_ENCODING);
		if (ENCODING_GZIP.equals(enc) || "x-gzip".equals(enc))
			in = new GZIPInputStream(in);
		else if (enc != null)
			throw new IOException(HDR_CONTENT_ENCODING + " \"" + enc + "\""
					+ ": not supported by this library.");
		return in;
	}

	public static void nocache(final HttpServletResponse rsp) {
		rsp.setHeader(HDR_EXPIRES
		rsp.setHeader(HDR_PRAGMA

		final String nocache = "no-cache
		rsp.setHeader(HDR_CACHE_CONTROL
	}

	public static void cacheForever(final HttpServletResponse rsp) {
		final long now = System.currentTimeMillis();
		rsp.setDateHeader(HDR_DATE
		rsp.setDateHeader(HDR_EXPIRES
		rsp.setHeader(HDR_CACHE_CONTROL
	}

	public static void sendPlainText(final String content
			final HttpServletRequest req
			throws IOException {
		final String enc = Constants.CHARACTER_ENCODING;
		final byte[] raw = content.getBytes(enc);
		rsp.setContentType("text/plain");
		rsp.setCharacterEncoding(enc);
		send(raw
	}

	public static void send(byte[] content
			final HttpServletResponse rsp) throws IOException {
		content = sendInit(content
		final OutputStream out = rsp.getOutputStream();
		try {
			out.write(content);
			out.flush();
		} finally {
			out.close();
		}
	}

	private static byte[] sendInit(byte[] content
			final HttpServletRequest req
			throws IOException {
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

	private ServletUtils() {
	}
}
