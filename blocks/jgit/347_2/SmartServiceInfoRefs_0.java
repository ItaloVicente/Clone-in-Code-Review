
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

	private boolean startedOutput;

	SmartOutputStream(final HttpServletRequest req
			final HttpServletResponse rsp) {
		super(LIMIT);
		this.req = req;
		this.rsp = rsp;
	}

	@Override
	protected OutputStream overflow() throws IOException {
		startedOutput = true;
		return rsp.getOutputStream();
	}

	public void close() throws IOException {
		super.close();

		if (!startedOutput) {
			TemporaryBuffer out = this;

			if (256 < out.length() && acceptsGzipEncoding(req)) {
				TemporaryBuffer gzbuf = new TemporaryBuffer.Heap(LIMIT);
				try {
					GZIPOutputStream gzip = new GZIPOutputStream(gzbuf);
					out.writeTo(gzip
					gzip.close();
					if (gzbuf.length() < out.length()) {
						out = gzbuf;
						rsp.setHeader(HDR_CONTENT_ENCODING
					}
				} catch (IOException err) {
				}
			}

			rsp.setContentLength((int) out.length());
			final OutputStream os = rsp.getOutputStream();
			try {
				out.writeTo(os
				os.flush();
			} finally {
				os.close();
			}
		}
	}
}
