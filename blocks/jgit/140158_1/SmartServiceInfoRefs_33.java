
package org.eclipse.jgit.http.server;

import static org.eclipse.jgit.http.server.ServletUtils.acceptsGzipEncoding;
import static org.eclipse.jgit.util.HttpSupport.ENCODING_GZIP;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_ENCODING;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.util.TemporaryBuffer;

class SmartOutputStream extends TemporaryBuffer {
	private static final int LIMIT = 32 * 1024;

	private final HttpServletRequest req;
	private final HttpServletResponse rsp;
	private boolean compressStream;
	private boolean startedOutput;

	SmartOutputStream(final HttpServletRequest req
			final HttpServletResponse rsp
			boolean compressStream) {
		super(LIMIT);
		this.req = req;
		this.rsp = rsp;
		this.compressStream = compressStream;
	}

	@Override
	protected OutputStream overflow() throws IOException {
		startedOutput = true;

		OutputStream out = rsp.getOutputStream();
		if (compressStream && acceptsGzipEncoding(req)) {
			rsp.setHeader(HDR_CONTENT_ENCODING
			out = new GZIPOutputStream(out);
		}
		return out;
	}

	@Override
	public void close() throws IOException {
		super.close();

		if (!startedOutput) {
			@SuppressWarnings("resource")
			TemporaryBuffer out = this;

			if (256 < out.length() && acceptsGzipEncoding(req)) {
				TemporaryBuffer gzbuf = new TemporaryBuffer.Heap(LIMIT);
				try {
					try (GZIPOutputStream gzip = new GZIPOutputStream(gzbuf)) {
						out.writeTo(gzip
					}
					if (gzbuf.length() < out.length()) {
						out = gzbuf;
						rsp.setHeader(HDR_CONTENT_ENCODING
					}
				} catch (IOException err) {
				}
			}

			rsp.setContentLength((int) out.length());
			try (OutputStream os = rsp.getOutputStream()) {
				out.writeTo(os
				os.flush();
			}
		}
	}
}
