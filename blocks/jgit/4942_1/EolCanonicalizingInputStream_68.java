
package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.diff.RawText;

public class AutoCRLFInputStream extends InputStream {
	private final byte[] single = new byte[1];

	private final byte[] buf = new byte[8096];

	private final InputStream in;

	private int cnt;

	private int ptr;

	private boolean isBinary;

	private boolean detectBinary;

	private byte last;

	public AutoCRLFInputStream(InputStream in
		this.in = in;
		this.detectBinary = detectBinary;
	}

	@Override
	public int read() throws IOException {
		final int read = read(single
		return read == 1 ? single[0] & 0xff : -1;
	}

	@Override
	public int read(byte[] bs
		if (len == 0)
			return 0;

		if (cnt == -1)
			return -1;

		final int startOff = off;
		final int end = off + len;

		while (off < end) {
			if (ptr == cnt && !fillBuffer())
				break;

			byte b = buf[ptr++];
			if (isBinary || b != '\n') {
				bs[off++] = last = b;
				continue;
			}

			if (b == '\n') {
				if (last == '\r') {
					bs[off++] = last = b;
					continue;
				}
				bs[off++] = last = '\r';
				ptr--;
			} else
				bs[off++] = last = b;
		}
		int n = startOff == off ? -1 : off - startOff;
		if (n > 0)
			last = bs[off - 1];
		return n;
	}

	@Override
	public void close() throws IOException {
		in.close();
	}

	private boolean fillBuffer() throws IOException {
		cnt = in.read(buf
		if (cnt < 1)
			return false;
		if (detectBinary) {
			isBinary = RawText.isBinary(buf
			detectBinary = false;
		}
		ptr = 0;
		return true;
	}
}
