
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT;
import static javax.servlet.http.HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE;
import static org.eclipse.jgit.util.HttpSupport.HDR_ACCEPT_RANGES;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_LENGTH;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_RANGE;
import static org.eclipse.jgit.util.HttpSupport.HDR_IF_RANGE;
import static org.eclipse.jgit.util.HttpSupport.HDR_RANGE;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.MessageFormat;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.ObjectId;

final class FileSender {
	private final File path;

	private final RandomAccessFile source;

	private final long lastModified;

	private final long fileLen;

	private long pos;

	private long end;

	FileSender(File path) throws FileNotFoundException {
		this.path = path;
		this.source = new RandomAccessFile(path

		try {
			this.lastModified = path.lastModified();
			this.fileLen = source.getChannel().size();
			this.end = fileLen;
		} catch (IOException e) {
			try {
				source.close();
			} catch (IOException closeError) {
			}

			final FileNotFoundException r;
			r = new FileNotFoundException(MessageFormat.format(HttpServerText.get().cannotGetLengthOf
			r.initCause(e);
			throw r;
		}
	}

	void close() {
		try {
			source.close();
		} catch (IOException e) {
		}
	}

	long getLastModified() {
		return lastModified;
	}

	String getTailChecksum() throws IOException {
		final int n = 20;
		final byte[] buf = new byte[n];
		source.seek(fileLen - n);
		source.readFully(buf
		return ObjectId.fromRaw(buf).getName();
	}

	void serve(final HttpServletRequest req
			final boolean sendBody) throws IOException {
		if (!initRangeRequest(req
			rsp.sendError(SC_REQUESTED_RANGE_NOT_SATISFIABLE);
			return;
		}

		rsp.setHeader(HDR_ACCEPT_RANGES
		rsp.setHeader(HDR_CONTENT_LENGTH

		if (sendBody) {
			try (OutputStream out = rsp.getOutputStream()) {
				final byte[] buf = new byte[4096];
				source.seek(pos);
				while (pos < end) {
					final int r = (int) Math.min(buf.length
					final int n = source.read(buf
					if (n < 0) {
						throw new EOFException(MessageFormat.format(HttpServerText.get().unexpectedeOFOn
					}
					out.write(buf
					pos += n;
				}
				out.flush();
			}
		}
	}

	private boolean initRangeRequest(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		final Enumeration<String> rangeHeaders = getRange(req);
		if (!rangeHeaders.hasMoreElements()) {
			return true;
		}

		final String range = rangeHeaders.nextElement();
		if (rangeHeaders.hasMoreElements()) {
			return false;
		}

		final int eq = range.indexOf('=');
		final int dash = range.indexOf('-');
		if (eq < 0 || dash < 0 || !range.startsWith("bytes=")) {
			return false;
		}

		final String ifRange = req.getHeader(HDR_IF_RANGE);
		if (ifRange != null && !getTailChecksum().equals(ifRange)) {
			return true;
		}

		try {
			if (eq + 1 == dash) {
				pos = Long.parseLong(range.substring(dash + 1));
				pos = fileLen - pos;
			} else {
				pos = Long.parseLong(range.substring(eq + 1
				if (dash < range.length() - 1) {
					end = Long.parseLong(range.substring(dash + 1));
				}
			}
		} catch (NumberFormatException e) {
				+ fileLen);
		source.seek(pos);
		return true;
	}

	private static Enumeration<String> getRange(HttpServletRequest req) {
		return req.getHeaders(HDR_RANGE);
	}
}
