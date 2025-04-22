
package org.eclipse.jgit.lib;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;
import static org.eclipse.jgit.lib.Constants.encode;
import static org.eclipse.jgit.lib.FileMode.GITLINK;
import static org.eclipse.jgit.lib.FileMode.REGULAR_FILE;
import static org.eclipse.jgit.lib.FileMode.TREE;

import java.io.IOException;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.TemporaryBuffer;

public class TreeFormatter {
	public static int entrySize(FileMode mode
		return mode.copyToLength() + nameLen + OBJECT_ID_LENGTH + 2;
	}

	private byte[] buf;

	private int ptr;

	private TemporaryBuffer.Heap overflowBuffer;

	public TreeFormatter() {
		this(8192);
	}

	public TreeFormatter(int size) {
		buf = new byte[size];
	}

	public void append(String name
		append(name
	}

	public void append(String name
		append(name
	}

	public void append(String name
		append(name
	}

	public void append(String name
		append(encode(name)
	}

	public void append(byte[] name
		append(name
	}

	public void append(byte[] nameBuf
			AnyObjectId id) {
		append(nameBuf
	}

	public void append(byte[] nameBuf
			AnyObjectId id
		if (nameLen == 0 && !allowEmptyName) {
			throw new IllegalArgumentException(
					JGitText.get().invalidTreeZeroLengthName);
		}
		if (fmtBuf(nameBuf
			id.copyRawTo(buf
			ptr += OBJECT_ID_LENGTH;

		} else {
			try {
				fmtOverflowBuffer(nameBuf
				id.copyRawTo(overflowBuffer);
			} catch (IOException badBuffer) {
				throw new RuntimeException(badBuffer);
			}
		}
	}

	public void append(byte[] nameBuf
			byte[] idBuf
		if (fmtBuf(nameBuf
			System.arraycopy(idBuf
			ptr += OBJECT_ID_LENGTH;

		} else {
			try {
				fmtOverflowBuffer(nameBuf
				overflowBuffer.write(idBuf
			} catch (IOException badBuffer) {
				throw new RuntimeException(badBuffer);
			}
		}
	}

	private boolean fmtBuf(byte[] nameBuf
			FileMode mode) {
		if (buf == null || buf.length < ptr + entrySize(mode
			return false;

		mode.copyTo(buf
		ptr += mode.copyToLength();
		buf[ptr++] = ' ';

		System.arraycopy(nameBuf
		ptr += nameLen;
		buf[ptr++] = 0;
		return true;
	}

	private void fmtOverflowBuffer(byte[] nameBuf
			FileMode mode) throws IOException {
		if (buf != null) {
			overflowBuffer = new TemporaryBuffer.Heap(Integer.MAX_VALUE);
			overflowBuffer.write(buf
			buf = null;
		}

		mode.copyTo(overflowBuffer);
		overflowBuffer.write((byte) ' ');
		overflowBuffer.write(nameBuf
		overflowBuffer.write((byte) 0);
	}

	public ObjectId insertTo(ObjectInserter ins) throws IOException {
		if (buf != null)
			return ins.insert(OBJ_TREE

		final long len = overflowBuffer.length();
		return ins.insert(OBJ_TREE
	}

	public ObjectId computeId(ObjectInserter ins) {
		if (buf != null)
			return ins.idFor(OBJ_TREE

		final long len = overflowBuffer.length();
		try {
			return ins.idFor(OBJ_TREE
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] toByteArray() {
		if (buf != null) {
			byte[] r = new byte[ptr];
			System.arraycopy(buf
			return r;
		}

		try {
			return overflowBuffer.toByteArray();
		} catch (IOException err) {
			throw new RuntimeException(err);
		}
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		byte[] raw = toByteArray();

		CanonicalTreeParser p = new CanonicalTreeParser();
		p.reset(raw);

		StringBuilder r = new StringBuilder();
		r.append("Tree={");
		if (!p.eof()) {
			r.append('\n');
			try {
				new ObjectChecker().checkTree(raw);
			} catch (CorruptObjectException error) {
				r.append("*** ERROR: ").append(error.getMessage()).append("\n");
				r.append('\n');
			}
		}
		while (!p.eof()) {
			final FileMode mode = p.getEntryFileMode();
			r.append(mode);
			r.append(' ');
			r.append(Constants.typeString(mode.getObjectType()));
			r.append(' ');
			r.append(p.getEntryObjectId().name());
			r.append(' ');
			r.append(p.getEntryPathString());
			r.append('\n');
			p.next();
		}
		r.append("}");
		return r.toString();
	}
}
