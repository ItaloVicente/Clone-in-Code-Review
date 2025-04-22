
package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.diff.RawText;

public class AutoLFOutputStream extends OutputStream {

	static final int BUFFER_SIZE = 8000;

	private final OutputStream out;

	private int buf = -1;

	private byte[] binbuf = new byte[BUFFER_SIZE];

	private byte[] onebytebuf = new byte[1];

	private int binbufcnt = 0;

	private boolean isBinary;

	public AutoLFOutputStream(OutputStream out) {
		this.out = out;
	}

	@Override
	public void write(int b) throws IOException {
		onebytebuf[0] = (byte) b;
		write(onebytebuf
	}

	@Override
	public void write(byte[] b) throws IOException {
		int overflow = buffer(b
		if (overflow > 0)
			write(b
	}

	@Override
	public void write(byte[] b
			throws IOException {
		final int overflow = buffer(b
		if (overflow < 0)
			return;
		final int off = startOff + startLen - overflow;
		final int len = overflow;
		if (len == 0)
			return;
		int lastw = off;
		if (isBinary) {
			out.write(b
			return;
		}
		for (int i = off; i < off + len; ++i) {
			final byte c = b[i];
			if (c == '\r') {
				if (lastw < i) {
					out.write(b
				}
				lastw = i + 1;
				buf = '\r';
			} else if (c == '\n') {
				if (buf == '\r') {
					out.write('\n');
					lastw = i + 1;
					buf = -1;
				} else {
					if (lastw < i + 1) {
						out.write(b
					}
					lastw = i + 1;
				}
			} else {
				if (buf == '\r') {
					out.write('\r');
					lastw = i;
				}
				buf = -1;
			}
		}
		if (lastw < off + len) {
			out.write(b
		}
	}

	private int buffer(byte[] b
		if (binbufcnt > binbuf.length)
			return len;
		int copy = Math.min(binbuf.length - binbufcnt
		System.arraycopy(b
		binbufcnt += copy;
		int remaining = len - copy;
		if (remaining > 0)
			decideMode();
		return remaining;
	}

	private void decideMode() throws IOException {
		isBinary = RawText.isBinary(binbuf
		int cachedLen = binbufcnt;
		write(binbuf
	}

	@Override
	public void flush() throws IOException {
		if (binbufcnt <= binbuf.length)
			decideMode();
		out.flush();
	}

	@Override
	public void close() throws IOException {
		flush();
		if (buf == '\r') {
			out.write(buf);
			buf = -1;
		}
		out.close();
	}
}
