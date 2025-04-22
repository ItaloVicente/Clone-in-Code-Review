
package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TeeInputStream extends InputStream {
	private byte[] skipBuffer;

	private InputStream src;

	private OutputStream dst;

	public TeeInputStream(InputStream src
		this.src = src;
		this.dst = dst;
	}

	@Override
	public int read() throws IOException {
		byte[] b = skipBuffer();
		int n = read(b
		return n == 1 ? b[0] & 0xff : -1;
	}

	@Override
	public long skip(long count) throws IOException {
		long skipped = 0;
		long cnt = count;
		final byte[] b = skipBuffer();
		while (0 < cnt) {
			final int n = src.read(b
			if (n <= 0)
				break;
			dst.write(b
			skipped += n;
			cnt -= n;
		}
		return skipped;
	}

	@Override
	public int read(byte[] b
		if (len == 0)
			return 0;

		int n = src.read(b
		if (0 < n)
			dst.write(b
		return n;
	}

	@Override
	public void close() throws IOException {
		byte[] b = skipBuffer();
		for (;;) {
			int n = src.read(b);
			if (n <= 0)
				break;
			dst.write(b
		}
		dst.close();
		src.close();
	}

	private byte[] skipBuffer() {
		if (skipBuffer == null)
			skipBuffer = new byte[2048];
		return skipBuffer;
	}
}
