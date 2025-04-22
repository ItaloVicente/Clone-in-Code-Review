
package org.eclipse.jgit.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class IO {

	public static final byte[] readFully(final File path)
			throws FileNotFoundException
		return IO.readFully(path
	}

	public static final byte[] readFully(final File path
			throws FileNotFoundException
		final FileInputStream in = new FileInputStream(path);
		try {
			final long sz = in.getChannel().size();
			if (sz > max)
				throw new IOException("File is too large: " + path);
			final byte[] buf = new byte[(int) sz];
			IO.readFully(in
			return buf;
		} finally {
			try {
				in.close();
			} catch (IOException ignored) {
			}
		}
	}

	public static void readFully(final InputStream fd
			int off
		while (len > 0) {
			final int r = fd.read(dst
			if (r <= 0)
				throw new EOFException("Short read of block.");
			off += r;
			len -= r;
		}
	}

	public static void readFully(final FileChannel fd
			final byte[] dst
		while (len > 0) {
			final int r = fd.read(ByteBuffer.wrap(dst
			if (r <= 0)
				throw new EOFException("Short read of block.");
			pos += r;
			off += r;
			len -= r;
		}
	}

	public static void skipFully(final InputStream fd
			throws IOException {
		while (toSkip > 0) {
			final long r = fd.skip(toSkip);
			if (r <= 0)
				throw new EOFException("Short skip of block");
			toSkip -= r;
		}
	}

	private IO() {
	}
}
