package org.eclipse.jgit.util.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class BinaryDeltaInputStream extends InputStream {

	private final byte[] base;

	private final InputStream delta;

	private long resultLength;

	private long toDeliver = -1;

	private int fromBase;

	private int fromDelta;

	private int baseOffset = -1;

	public BinaryDeltaInputStream(byte[] base
		this.base = base;
		this.delta = delta;
	}

	@Override
	public int read() throws IOException {
		int b = readNext();
		if (b >= 0) {
			toDeliver--;
		}
		return b;
	}

	private void initialize() throws IOException {
		long baseSize = readVarInt(delta);
		if (baseSize > Integer.MAX_VALUE || baseSize < 0
				|| (int) baseSize != base.length) {
			throw new IOException(MessageFormat.format(
					JGitText.get().binaryDeltaBaseLengthMismatch
					Integer.valueOf(base.length)
		}
		resultLength = readVarInt(delta);
		if (resultLength < 0) {
			throw new StreamCorruptedException(
					JGitText.get().binaryDeltaInvalidResultLength);
		}
		toDeliver = resultLength;
		baseOffset = 0;
	}

	private int readNext() throws IOException {
		if (baseOffset < 0) {
			initialize();
		}
		if (fromBase > 0) {
			fromBase--;
			return base[baseOffset++] & 0xFF;
		} else if (fromDelta > 0) {
			fromDelta--;
			return delta.read();
		}
		int command = delta.read();
		if (command < 0) {
			return -1;
		}
		if ((command & 0x80) != 0) {
			long copyOffset = 0;
			for (int i = 1
				if ((command & i) != 0) {
					copyOffset |= ((long) next(delta)) << shift;
				}
			}
			int copySize = 0;
			for (int i = 0x10
				if ((command & i) != 0) {
					copySize |= next(delta) << shift;
				}
			}
			if (copySize == 0) {
				copySize = 0x10000;
			}
			if (copyOffset > base.length - copySize) {
				throw new StreamCorruptedException(MessageFormat.format(
						JGitText.get().binaryDeltaInvalidOffset
						Long.valueOf(copyOffset)
			}
			baseOffset = (int) copyOffset;
			fromBase = copySize;
			return readNext();
		} else if (command != 0) {
			fromDelta = command - 1;
			return delta.read();
		} else {
			throw new StreamCorruptedException(
					JGitText.get().unsupportedCommand0);
		}
	}

	private int next(InputStream in) throws IOException {
		int b = in.read();
		if (b < 0) {
			throw new EOFException();
		}
		return b;
	}

	private long readVarInt(InputStream in) throws IOException {
		long val = 0;
		int shift = 0;
		int b;
		do {
			b = next(in);
			val |= ((long) (b & 0x7f)) << shift;
			shift += 7;
		} while ((b & 0x80) != 0);
		return val;
	}

	public long getExpectedResultSize() throws IOException {
		if (baseOffset < 0) {
			initialize();
		}
		return resultLength;
	}

	public boolean isFullyConsumed() {
		try {
			return toDeliver == 0 && delta.read() < 0;
		} catch (IOException e) {
			return toDeliver == 0;
		}
	}

	@Override
	public void close() throws IOException {
		delta.close();
	}
}
