
package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.diff.RawText;

public class AutoLFInputStream extends InputStream {
	private final byte[] single = new byte[1];

	private final byte[] buf = new byte[8096];

	private final InputStream in;

	private int cnt;

	private int ptr;

	private boolean isBinary;

	private boolean detectBinary;

	private boolean abortIfBinary;

	public static class IsBinaryException extends IOException {
		private static final long serialVersionUID = 1L;

		IsBinaryException() {
			super();
		}
	}

	public AutoLFInputStream(InputStream in
		this(in
	}

	public AutoLFInputStream(InputStream in
			boolean abortIfBinary) {
		this.in = in;
		this.detectBinary = detectBinary;
		this.abortIfBinary = abortIfBinary;
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

		int i = off;
		final int end = off + len;

		while (i < end) {
			if (ptr == cnt && !fillBuffer()) {
				break;
			}

			byte b = buf[ptr++];
			if (isBinary || b != '\r') {
				bs[i++] = b;
				continue;
			}

			if (ptr == cnt && !fillBuffer()) {
				bs[i++] = '\r';
				break;
			}

			if (buf[ptr] == '\n') {
				bs[i++] = '\n';
				ptr++;
			} else
				bs[i++] = '\r';
		}

		return i == off ? -1 : i - off;
	}

	public boolean isBinary() {
		return isBinary;
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
			if (isBinary && abortIfBinary)
				throw new IsBinaryException();
		}
		ptr = 0;
		return true;
	}
}
