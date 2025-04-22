
package org.eclipse.jgit.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.io.SilentFileInputStream;

public class IO {

	public static final byte[] readFully(File path)
			throws FileNotFoundException
		return IO.readFully(path
	}

	public static final byte[] readSome(File path
			throws FileNotFoundException
		try (SilentFileInputStream in = new SilentFileInputStream(path)) {
			byte[] buf = new byte[limit];
			int cnt = 0;
			for (;;) {
				int n = in.read(buf
				if (n <= 0)
					break;
				cnt += n;
			}
			if (cnt == buf.length)
				return buf;
			byte[] res = new byte[cnt];
			System.arraycopy(buf
			return res;
		}
	}

	public static final byte[] readFully(File path
			throws FileNotFoundException
		try (SilentFileInputStream in = new SilentFileInputStream(path)) {
			long sz = Math.max(path.length()
			if (sz > max)
				throw new IOException(MessageFormat.format(
						JGitText.get().fileIsTooLarge

			byte[] buf = new byte[(int) sz];
			int valid = 0;
			for (;;) {
				if (buf.length == valid) {
					if (buf.length == max) {
						int next = in.read();
						if (next < 0)
							break;

						throw new IOException(MessageFormat.format(
								JGitText.get().fileIsTooLarge
					}

					byte[] nb = new byte[Math.min(buf.length * 2
					System.arraycopy(buf
					buf = nb;
				}
				int n = in.read(buf
				if (n < 0)
					break;
				valid += n;
			}
			if (valid < buf.length) {
				byte[] nb = new byte[valid];
				System.arraycopy(buf
				buf = nb;
			}
			return buf;
		}
	}

	public static ByteBuffer readWholeStream(InputStream in
			throws IOException {
		byte[] out = new byte[sizeHint];
		int pos = 0;
		while (pos < out.length) {
			int read = in.read(out
			if (read < 0)
				return ByteBuffer.wrap(out
			pos += read;
		}

		int last = in.read();
		if (last < 0)
			return ByteBuffer.wrap(out

		try (TemporaryBuffer.Heap tmp = new TemporaryBuffer.Heap(
				Integer.MAX_VALUE)) {
			tmp.write(out);
			tmp.write(last);
			tmp.copy(in);
			return ByteBuffer.wrap(tmp.toByteArray());
		}
	}

	public static void readFully(final InputStream fd
			int off
		while (len > 0) {
			final int r = fd.read(dst
			if (r <= 0)
				throw new EOFException(JGitText.get().shortReadOfBlock);
			off += r;
			len -= r;
		}
	}

	public static int read(ReadableByteChannel channel
			int len) throws IOException {
		if (len == 0)
			return 0;
		int cnt = 0;
		while (0 < len) {
			int r = channel.read(ByteBuffer.wrap(dst
			if (r <= 0)
				break;
			off += r;
			len -= r;
			cnt += r;
		}
		return cnt != 0 ? cnt : -1;
	}

	public static int readFully(InputStream fd
			throws IOException {
		int r;
		int len = 0;
		while ((r = fd.read(dst
				&& len < dst.length) {
			off += r;
			len += r;
		}
		return len;
	}

	public static void skipFully(InputStream fd
			throws IOException {
		while (toSkip > 0) {
			final long r = fd.skip(toSkip);
			if (r <= 0)
				throw new EOFException(JGitText.get().shortSkipOfBlock);
			toSkip -= r;
		}
	}

	public static List<String> readLines(String s) {
		List<String> l = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\n') {
				l.add(sb.toString());
				sb.setLength(0);
				continue;
			}
			if (c == '\r') {
				if (i + 1 < s.length()) {
					c = s.charAt(++i);
					l.add(sb.toString());
					sb.setLength(0);
					if (c != '\n')
						sb.append(c);
					continue;
					l.add(sb.toString());
					break;
				}
			}
			sb.append(c);
		}
		l.add(sb.toString());
		return l;
	}

	public static String readLine(Reader in
		if (in.markSupported()) {
			if (sizeHint <= 0) {
				sizeHint = 1024;
			}
			StringBuilder sb = new StringBuilder(sizeHint);
			char[] buf = new char[sizeHint];
			while (true) {
				in.mark(sizeHint);
				int n = in.read(buf);
				if (n < 0) {
					in.reset();
					return sb.toString();
				}
				for (int i = 0; i < n; i++) {
					if (buf[i] == '\n') {
						resetAndSkipFully(in
						sb.append(buf
						return sb.toString();
					}
				}
				if (n > 0) {
					sb.append(buf
				}
				resetAndSkipFully(in
			}
		} else {
			StringBuilder buf = sizeHint > 0
					? new StringBuilder(sizeHint)
					: new StringBuilder();
			int i;
			while ((i = in.read()) != -1) {
				char c = (char) i;
				buf.append(c);
				if (c == '\n') {
					break;
				}
			}
			return buf.toString();
		}
	}

	private static void resetAndSkipFully(Reader fd
		fd.reset();
		while (toSkip > 0) {
			long r = fd.skip(toSkip);
			if (r <= 0) {
				throw new EOFException(JGitText.get().shortSkipOfBlock);
			}
			toSkip -= r;
		}
	}

	private IO() {
	}
}
