
package org.eclipse.jgit.util.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.internal.JGitText;

public abstract class LimitedInputStream extends FilterInputStream {

	private long left;
	protected final long limit;
	private long mark = -1;

	protected LimitedInputStream(InputStream in
		super(in);
		left = limit;
		this.limit = limit;
	}

	@Override
	public int available() throws IOException {
		return (int) Math.min(in.available()
	}

	@Override
	public synchronized void mark(int readLimit) {
		in.mark(readLimit);
		mark = left;
	}

	@Override
	public int read() throws IOException {
		if (left == 0) {
			if (in.available() == 0)
				return -1;
			else
				limitExceeded();
		}

		int result = in.read();
		if (result != -1)
			--left;
		return result;
	}

	@Override
	public int read(byte[] b
		if (left == 0) {
			if (in.available() == 0)
				return -1;
			else
				limitExceeded();
		}

		len = (int) Math.min(len
		int result = in.read(b
		if (result != -1)
			left -= result;
		return result;
	}

	@Override
	public synchronized void reset() throws IOException {
		if (!in.markSupported())
			throw new IOException(JGitText.get().unsupportedMark);

		if (mark == -1)
			throw new IOException(JGitText.get().unsetMark);

		in.reset();
		left = mark;
	}

	@Override
	public long skip(long n) throws IOException {
		n = Math.min(n
		long skipped = in.skip(n);
		left -= skipped;
		return skipped;
	}

	protected abstract void limitExceeded() throws IOException;
}
