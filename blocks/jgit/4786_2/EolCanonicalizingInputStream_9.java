
package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.diff.RawText;

public class AutoCRLFOutputStream extends OutputStream {

	private final OutputStream out;

	private int buf = -1;

	private byte[] binbuf = new byte[8000];

	private int binbufcnt = 0;

	private boolean isBinary;

	public AutoCRLFOutputStream(OutputStream out) {
		this.out = out;
	}

	@Override
	public void write(int b) throws IOException {
		int overflow = buffer((byte) b);
		if (overflow >= 0)
			return;
		if (isBinary) {
			out.write(b);
			return;
		}
		if (b == '\n') {
			if (buf == '\r') {
				out.write('\n');
				buf = -1;
			} else if (buf == -1) {
				out.write('\r');
				out.write('\n');
				buf = -1;
			}
		} else if (b == '\r') {
			out.write(b);
			buf = '\r';
		} else {
			out.write(b);
			buf = -1;
		}
	}

	@Override
	public void write(byte[] b) throws IOException {
		int overflow = buffer(b
		if (overflow > 0)
			write(b
	}

	@Override
	public void write(byte[] b
		int overflow = buffer(b
		if (overflow < 0)
			return;
		off = off + len - overflow;
		len = overflow;
		if (len == 0)
			return;
		int lastw = off;
		if (isBinary) {
			out.write(b
			return;
		}
		for (int i = off; i < off + len; ++i) {
			byte c = b[i];
			if (c == '\r') {
				buf = '\r';
			} else if (c == '\n') {
				if (buf != '\r') {
					if (lastw < i) {
						out.write(b
					}
					out.write('\r');
					lastw = i;
				}
				buf = -1;
			} else {
				buf = -1;
			}
		}
		if (lastw < off + len) {
			out.write(b
		}
		if (b[off + len - 1] == '\r')
			buf = '\r';
	}

	private int buffer(byte b) throws IOException {
		if (binbufcnt > binbuf.length)
			return 1;
		binbuf[binbufcnt++] = b;
		if (binbufcnt == binbuf.length)
			decideMode();
		return 0;
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
		if (binbufcnt < binbuf.length)
			decideMode();
		buf = -1;
	}

	@Override
	public void close() throws IOException {
		flush();
		super.close();
	}
}
