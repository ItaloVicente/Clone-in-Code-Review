
package org.eclipse.jgit.diff;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jgit.errors.BinaryBlobException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.IntList;
import org.eclipse.jgit.util.RawParseUtils;

public class RawText extends Sequence {
	public static final RawText EMPTY_TEXT = new RawText(new byte[0]);

	static final int FIRST_FEW_BYTES = 8000;

	protected final byte[] content;

	protected final IntList lines;

	public RawText(byte[] input) {
		this(input
	}

	public RawText(byte[] input
		content = input;
		lines = lineMap;
	}

	public RawText(File file) throws IOException {
		this(IO.readFully(file));
	}

	public byte[] getRawContent() {
		return content;
	}

	@Override
	public int size() {
		return lines.size() - 2;
	}

	public void writeLine(OutputStream out
			throws IOException {
		int start = getStart(i);
		int end = getEnd(i);
		if (content[end - 1] == '\n')
			end--;
		out.write(content
	}

	public boolean isMissingNewlineAtEnd() {
		final int end = lines.get(lines.size() - 1);
		if (end == 0)
			return true;
		return content[end - 1] != '\n';
	}

	public String getString(int i) {
		return getString(i
	}

	public String getString(int begin
		if (begin == end)

		int s = getStart(begin);
		int e = getEnd(end - 1);
		if (dropLF && content[e - 1] == '\n')
			e--;
		return decode(s
	}

	protected String decode(int start
		return RawParseUtils.decode(content
	}

	private int getStart(int i) {
		return lines.get(i + 1);
	}

	private int getEnd(int i) {
		return lines.get(i + 2);
	}

	public static boolean isBinary(byte[] raw) {
		return isBinary(raw
	}

	public static boolean isBinary(InputStream raw) throws IOException {
		final byte[] buffer = new byte[FIRST_FEW_BYTES];
		int cnt = 0;
		while (cnt < buffer.length) {
			final int n = raw.read(buffer
			if (n == -1)
				break;
			cnt += n;
		}
		return isBinary(buffer
	}

	public static boolean isBinary(byte[] raw
		if (length > FIRST_FEW_BYTES)
			length = FIRST_FEW_BYTES;
		for (int ptr = 0; ptr < length; ptr++)
			if (raw[ptr] == '\0')
				return true;

		return false;
	}

	public static boolean isCrLfText(byte[] raw) {
		return isCrLfText(raw
	}

	public static boolean isCrLfText(InputStream raw) throws IOException {
		byte[] buffer = new byte[FIRST_FEW_BYTES];
		int cnt = 0;
		while (cnt < buffer.length) {
			int n = raw.read(buffer
			if (n == -1) {
				break;
			}
			cnt += n;
		}
		return isCrLfText(buffer
	}

	public static boolean isCrLfText(byte[] raw
		boolean has_crlf = false;
		for (int ptr = 0; ptr < length - 1; ptr++) {
			if (raw[ptr] == '\0') {
			} else if (raw[ptr] == '\r' && raw[ptr + 1] == '\n') {
				has_crlf = true;
			}
		}
		return has_crlf;
	}

	public String getLineDelimiter() {
		if (size() == 0)
			return null;
		int e = getEnd(0);
		if (content[e - 1] != '\n')
			return null;
		if (content.length > 1 && e > 1 && content[e - 2] == '\r')
		else
	}

	public static RawText load(ObjectLoader ldr
			throws IOException
		long sz = ldr.getSize();

		if (sz > threshold) {
			throw new BinaryBlobException();
		}

		if (sz <= FIRST_FEW_BYTES) {
			byte[] data = ldr.getCachedBytes(FIRST_FEW_BYTES);
			if (isBinary(data)) {
				throw new BinaryBlobException();
			}
			return new RawText(data);
		}

		byte[] head = new byte[FIRST_FEW_BYTES];
		try (InputStream stream = ldr.openStream()) {
			int off = 0;
			int left = head.length;
			while (left > 0) {
				int n = stream.read(head
				if (n < 0) {
					throw new EOFException();
				}
				left -= n;

				while (n > 0) {
					if (head[off] == '\0') {
						throw new BinaryBlobException();
					}
					off++;
					n--;
				}
			}

			byte data[];
			try {
				data = new byte[(int)sz];
			} catch (OutOfMemoryError e) {
				throw new LargeObjectException.OutOfMemory(e);
			}

			System.arraycopy(head
			IO.readFully(stream
			return new RawText(data
		}
	}
}
