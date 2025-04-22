
package org.eclipse.jgit.patch;

import static org.eclipse.jgit.lib.Constants.encodeASCII;
import static org.eclipse.jgit.util.RawParseUtils.match;
import static org.eclipse.jgit.util.RawParseUtils.nextLF;
import static org.eclipse.jgit.util.RawParseUtils.parseBase10;

public class BinaryHunk {


	public static enum Type {
		LITERAL_DEFLATED

		DELTA_DEFLATED;
	}

	private final FileHeader file;

	final int startOffset;

	int endOffset;

	private Type type;

	private int length;

	BinaryHunk(FileHeader fh
		file = fh;
		startOffset = offset;
	}

	public FileHeader getFileHeader() {
		return file;
	}

	public byte[] getBuffer() {
		return file.buf;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public Type getType() {
		return type;
	}

	public int getSize() {
		return length;
	}

	int parseHunk(int ptr
		final byte[] buf = file.buf;

		if (match(buf
			type = Type.LITERAL_DEFLATED;
			length = parseBase10(buf

		} else if (match(buf
			type = Type.DELTA_DEFLATED;
			length = parseBase10(buf

		} else {
			return -1;
		}
		ptr = nextLF(buf

		while (ptr < end) {
			final boolean empty = buf[ptr] == '\n';
			ptr = nextLF(buf
			if (empty)
				break;
		}

		return ptr;
	}
}
