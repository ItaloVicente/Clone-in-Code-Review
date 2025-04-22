
package org.eclipse.jgit.http.server;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.util.HttpSupport.ENCODING_GZIP;
import static org.eclipse.jgit.util.HttpSupport.ENCODING_X_GZIP;
import static org.eclipse.jgit.util.HttpSupport.HDR_ACCEPT_ENCODING;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_ENCODING;
import static org.eclipse.jgit.util.HttpSupport.HDR_ETAG;
import static org.eclipse.jgit.util.HttpSupport.TEXT_PLAIN;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.internal.storage.dfs.DfsRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public final class ServletUtils {
	public static final String ATTRIBUTE_REPOSITORY = "org.eclipse.jgit.Repository";

	public static final String ATTRIBUTE_HANDLER = "org.eclipse.jgit.transport.UploadPackOrReceivePack";

	public static Repository getRepository(ServletRequest req) {
		Repository db = (Repository) req.getAttribute(ATTRIBUTE_REPOSITORY);
		if (db == null)
			throw new IllegalStateException(HttpServerText.get().expectedRepositoryAttribute);
		return db;
	}

	public static InputStream getInputStream(HttpServletRequest req)
			throws IOException {
		InputStream in = req.getInputStream();
		final String enc = req.getHeader(HDR_CONTENT_ENCODING);
		if (ENCODING_GZIP.equals(enc) || ENCODING_X_GZIP.equals(enc))
			in = new GZIPInputStream(in);
		else if (enc != null)
			throw new IOException(MessageFormat.format(HttpServerText.get().encodingNotSupportedByThisLibrary
					
		return in;
	}

	public static void consumeRequestBody(HttpServletRequest req) {
		if (0 < req.getContentLength() || isChunked(req)) {
			try {
				consumeRequestBody(req.getInputStream());
			} catch (IOException e) {
			}
		}
	}

	static boolean isChunked(HttpServletRequest req) {
		return "chunked".equals(req.getHeader("Transfer-Encoding"));
	}

	public static void consumeRequestBody(InputStream in) {
		if (in == null)
			return;
		try {
			while (0 < in.skip(2048) || 0 <= in.read()) {
			}
		} catch (IOException err) {
		} finally {
			try {
				in.close();
			} catch (IOException err) {
			}
		}
	}

	public static void sendPlainText(final String content
			final HttpServletRequest req
			throws IOException {
		final byte[] raw = content.getBytes(UTF_8);
		rsp.setContentType(TEXT_PLAIN);
		rsp.setCharacterEncoding(UTF_8.name());
		send(raw
	}

	public static void send(byte[] content
			final HttpServletResponse rsp) throws IOException {
		content = sendInit(content
		try (OutputStream out = rsp.getOutputStream()) {
			out.write(content);
			out.flush();
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

	static boolean acceptsGzipEncoding(HttpServletRequest req) {
		return acceptsGzipEncoding(req.getHeader(HDR_ACCEPT_ENCODING));
	}

	static boolean acceptsGzipEncoding(String accepts) {
		if (accepts == null)
			return false;

		int b = 0;
		while (b < accepts.length()) {
			int comma = accepts.indexOf('
			int e = 0 <= comma ? comma : accepts.length();
			String term = accepts.substring(b
			if (term.equals(ENCODING_GZIP))
				return true;
			b = e + 1;
		}
		return false;
	}

	private static byte[] compress(byte[] raw) throws IOException {
		final int maxLen = raw.length + 32;
		final ByteArrayOutputStream out = new ByteArrayOutputStream(maxLen);
		final GZIPOutputStream gz = new GZIPOutputStream(out);
		gz.write(raw);
		gz.finish();
		gz.flush();
		return out.toByteArray();
	}

	private static String etag(byte[] content) {
		final MessageDigest md = Constants.newMessageDigest();
		md.update(content);
		return ObjectId.fromRaw(md.digest()).getName();
	}

	static String identify(Repository git) {
		if (git instanceof DfsRepository) {
			return ((DfsRepository) git).getDescription().getRepositoryName();
		} else if (git.getDirectory() != null) {
			return git.getDirectory().getPath();
		}
		return "unknown";
	}

	private ServletUtils() {
	}
}
