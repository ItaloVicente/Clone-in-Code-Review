
package org.eclipse.jgit.storage.pack;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.util.IO;

public abstract class DeltaStream extends InputStream {
	private static final int CMD_COPY = 0;

	private static final int CMD_INSERT = 1;

	private static final int CMD_EOF = 2;

	private final InputStream deltaStream;

	private long baseSize;

	private long resultSize;

	private final byte[] cmdbuf = new byte[512];

	private int cmdptr;

	private int cmdcnt;

	private InputStream baseStream;

	private long baseOffset;

	private int curcmd;

	private long copyOffset;

	private int copySize;

	public DeltaStream(final InputStream deltaStream) throws IOException {
		this.deltaStream = deltaStream;
		if (!fill(cmdbuf.length))
			throw new EOFException();

		int c
		do {
			c = cmdbuf[cmdptr++] & 0xff;
			baseSize |= (c & 0x7f) << shift;
			shift += 7;
		} while ((c & 0x80) != 0);

		shift = 0;
		do {
			c = cmdbuf[cmdptr++] & 0xff;
			resultSize |= (c & 0x7f) << shift;
			shift += 7;
		} while ((c & 0x80) != 0);

		curcmd = next();
	}

	protected abstract InputStream openBase() throws IOException;

	protected abstract long getBaseSize() throws IOException;

	public long getSize() {
		return resultSize;
	}

	@Override
	public int read() throws IOException {
		byte[] buf = new byte[1];
		int n = read(buf
		return n == 1 ? buf[0] & 0xff : -1;
	}

	@Override
	public void close() throws IOException {
		deltaStream.close();
		if (baseStream != null)
			baseStream.close();
	}

	@Override
	public long skip(long len) throws IOException {
		long act = 0;
		while (0 < len) {
			long n = Math.min(len
			switch (curcmd) {
			case CMD_COPY:
				copyOffset += n;
				break;

			case CMD_INSERT:
				cmdptr += n;
				break;

			case CMD_EOF:
				return act;
			default:
				throw new CorruptObjectException(
						JGitText.get().unsupportedCommand0);
			}

			act += n;
			len -= n;
			copySize -= n;
			if (copySize == 0)
				curcmd = next();
		}
		return act;
	}

	@Override
	public int read(byte[] buf
		int act = 0;
		while (0 < len) {
			int n = Math.min(len
			switch (curcmd) {
			case CMD_COPY:
				seekBase();
				n = baseStream.read(buf
				if (n < 0)
					throw new CorruptObjectException(
							JGitText.get().baseLengthIncorrect);
				baseOffset += n;
				break;

			case CMD_INSERT:
				System.arraycopy(cmdbuf
				cmdptr += n;
				break;

			case CMD_EOF:
				return 0 < act ? act : -1;
			default:
				throw new CorruptObjectException(
						JGitText.get().unsupportedCommand0);
			}

			act += n;
			off += n;
			len -= n;
			copySize -= n;
			if (copySize == 0)
				curcmd = next();
		}
		return act;
	}

	private boolean fill(final int need) throws IOException {
		int n = have();
		if (need < n)
			return true;
		if (n == 0) {
			cmdptr = 0;
			cmdcnt = 0;
		} else if (cmdbuf.length - cmdptr < need) {
			System.arraycopy(cmdbuf
			cmdptr = 0;
			cmdcnt = n;
		}

		do {
			n = deltaStream.read(cmdbuf
			if (n < 0)
				return 0 < have();
			cmdcnt += n;
		} while (cmdcnt < cmdbuf.length);
		return true;
	}

	private int next() throws IOException {
		if (!fill(8))
			return CMD_EOF;

		final int cmd = cmdbuf[cmdptr++] & 0xff;
		if ((cmd & 0x80) != 0) {
			copyOffset = 0;
			if ((cmd & 0x01) != 0)
				copyOffset = cmdbuf[cmdptr++] & 0xff;
			if ((cmd & 0x02) != 0)
				copyOffset |= (cmdbuf[cmdptr++] & 0xff) << 8;
			if ((cmd & 0x04) != 0)
				copyOffset |= (cmdbuf[cmdptr++] & 0xff) << 16;
			if ((cmd & 0x08) != 0)
				copyOffset |= (cmdbuf[cmdptr++] & 0xff) << 24;

			copySize = 0;
			if ((cmd & 0x10) != 0)
				copySize = cmdbuf[cmdptr++] & 0xff;
			if ((cmd & 0x20) != 0)
				copySize |= (cmdbuf[cmdptr++] & 0xff) << 8;
			if ((cmd & 0x40) != 0)
				copySize |= (cmdbuf[cmdptr++] & 0xff) << 16;
			if (copySize == 0)
				copySize = 0x10000;
			return CMD_COPY;

		} else if (cmd != 0) {
			fill(cmd);
			copySize = cmd;
			return CMD_INSERT;

		} else {
			throw new CorruptObjectException(JGitText.get().unsupportedCommand0);
		}
	}

	private int have() {
		return cmdcnt - cmdptr;
	}

	private void seekBase() throws IOException {
		if (baseStream == null) {
			baseStream = openBase();
			if (getBaseSize() != baseSize)
				throw new CorruptObjectException(
						JGitText.get().baseLengthIncorrect);
			IO.skipFully(baseStream
			baseOffset = copyOffset;

		} else if (baseOffset < copyOffset) {
			IO.skipFully(baseStream
			baseOffset = copyOffset;

		} else if (baseOffset > copyOffset) {
			baseStream.close();
			baseStream = openBase();
			IO.skipFully(baseStream
			baseOffset = copyOffset;
		}
	}
}
