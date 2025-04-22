package org.eclipse.jgit.util.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.Base85;

public class BinaryHunkInputStream extends InputStream {

	private final InputStream in;

	private int lineNumber;

	private byte[] buffer;

	private int pos = 0;

	public BinaryHunkInputStream(InputStream in) {
		this.in = in;
	}

	@Override
	public int read() throws IOException {
		if (pos < 0) {
			return -1;
		}
		if (buffer == null || pos == buffer.length) {
			fillBuffer();
		}
		if (pos >= 0) {
			return buffer[pos++] & 0xFF;
		}
		return -1;
	}

	@Override
	public int read(byte[] b
		return super.read(b
	}

	@Override
	public void close() throws IOException {
		in.close();
		buffer = null;
	}

	private void fillBuffer() throws IOException {
		int length = in.read();
		if (length < 0) {
			pos = length;
			buffer = null;
			return;
		}
		lineNumber++;
		if ('A' <= length && length <= 'Z') {
			length = length - 'A' + 1;
		} else if ('a' <= length && length <= 'z') {
			length = length - 'a' + 27;
		} else {
			throw new StreamCorruptedException(MessageFormat.format(
					JGitText.get().binaryHunkInvalidLength
					Integer.valueOf(lineNumber)
		}
		byte[] encoded = new byte[Base85.encodedLength(length)];
		for (int i = 0; i < encoded.length; i++) {
			int b = in.read();
			if (b < 0 || b == '\n') {
				throw new EOFException(MessageFormat.format(
						JGitText.get().binaryHunkInvalidLength
						Integer.valueOf(lineNumber)));
			}
			encoded[i] = (byte) b;
		}
		int b = in.read();
		if (b >= 0 && b != '\n') {
			throw new StreamCorruptedException(MessageFormat.format(
					JGitText.get().binaryHunkMissingNewline
					Integer.valueOf(lineNumber)));
		}
		try {
			buffer = Base85.decode(encoded
		} catch (IllegalArgumentException e) {
			StreamCorruptedException ex = new StreamCorruptedException(
					MessageFormat.format(JGitText.get().binaryHunkDecodeError
							Integer.valueOf(lineNumber)));
			ex.initCause(e);
			throw ex;
		}
		pos = 0;
	}
}
