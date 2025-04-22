
package org.eclipse.jgit.treewalk;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.eclipse.jgit.attributes.AttributesHandler;
import org.eclipse.jgit.attributes.AttributesNode;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.util.Paths;

public abstract class AbstractTreeIterator {
	protected static final int DEFAULT_PATH_SIZE = 128;

	protected static final byte[] zeroid = new byte[Constants.OBJECT_ID_LENGTH];

	public final AbstractTreeIterator parent;

	AbstractTreeIterator matches;

	protected AttributesNode attributesNode;

	int matchShift;

	protected int mode;

	protected byte[] path;

	protected final int pathOffset;

	protected int pathLen;

	protected AbstractTreeIterator() {
		parent = null;
		path = new byte[DEFAULT_PATH_SIZE];
		pathOffset = 0;
	}

	protected AbstractTreeIterator(String prefix) {
		parent = null;

		if (prefix != null && prefix.length() > 0) {
			final ByteBuffer b;

			b = UTF_8.encode(CharBuffer.wrap(prefix));
			pathLen = b.limit();
			path = new byte[Math.max(DEFAULT_PATH_SIZE
			b.get(path
			if (path[pathLen - 1] != '/')
				path[pathLen++] = '/';
			pathOffset = pathLen;
		} else {
			path = new byte[DEFAULT_PATH_SIZE];
			pathOffset = 0;
		}
	}

	protected AbstractTreeIterator(byte[] prefix) {
		parent = null;

		if (prefix != null && prefix.length > 0) {
			pathLen = prefix.length;
			path = new byte[Math.max(DEFAULT_PATH_SIZE
			System.arraycopy(prefix
			if (path[pathLen - 1] != '/')
				path[pathLen++] = '/';
			pathOffset = pathLen;
		} else {
			path = new byte[DEFAULT_PATH_SIZE];
			pathOffset = 0;
		}
	}

	protected AbstractTreeIterator(AbstractTreeIterator p) {
		parent = p;
		path = p.path;
		pathOffset = p.pathLen + 1;

		try {
			path[pathOffset - 1] = '/';
		} catch (ArrayIndexOutOfBoundsException e) {
			growPath(p.pathLen);
			path[pathOffset - 1] = '/';
		}
	}

	protected AbstractTreeIterator(final AbstractTreeIterator p
			final byte[] childPath
		parent = p;
		path = childPath;
		pathOffset = childPathOffset;
	}

	protected void growPath(int len) {
		setPathCapacity(path.length << 1
	}

	protected void ensurePathCapacity(int capacity
		if (path.length >= capacity)
			return;
		final byte[] o = path;
		int current = o.length;
		int newCapacity = current;
		while (newCapacity < capacity && newCapacity > 0)
			newCapacity <<= 1;
		setPathCapacity(newCapacity
	}

	private void setPathCapacity(int capacity
		final byte[] o = path;
		final byte[] n = new byte[capacity];
		System.arraycopy(o
		for (AbstractTreeIterator p = this; p != null && p.path == o; p = p.parent)
			p.path = n;
	}

	public int pathCompare(AbstractTreeIterator p) {
		return pathCompare(p
	}

	int pathCompare(AbstractTreeIterator p
		int cPos = alreadyMatch(this
		return pathCompare(p.path
	}

	public boolean findFile(String name) throws CorruptObjectException {
		return findFile(Constants.encode(name));
	}

	public boolean findFile(byte[] name) throws CorruptObjectException {
		for (; !eof(); next(1)) {
			int cmp = pathCompare(name
			if (cmp == 0) {
				return true;
			} else if (cmp > 0) {
				return false;
			}
		}
		return false;
	}

	public int pathCompare(byte[] buf
		return pathCompare(buf
	}

	private int pathCompare(byte[] b
		return Paths.compare(
				path
				b
	}

	private static int alreadyMatch(AbstractTreeIterator a
			AbstractTreeIterator b) {
		for (;;) {
			final AbstractTreeIterator ap = a.parent;
			final AbstractTreeIterator bp = b.parent;
			if (ap == null || bp == null)
				return 0;
			if (ap.matches == bp.matches)
				return a.pathOffset;
			a = ap;
			b = bp;
		}
	}

	public boolean idEqual(AbstractTreeIterator otherIterator) {
		return ObjectId.equals(idBuffer()
				otherIterator.idBuffer()
	}

	public abstract boolean hasId();

	public ObjectId getEntryObjectId() {
		return ObjectId.fromRaw(idBuffer()
	}

	public void getEntryObjectId(MutableObjectId out) {
		out.fromRaw(idBuffer()
	}

	public FileMode getEntryFileMode() {
		return FileMode.fromBits(mode);
	}

	public int getEntryRawMode() {
		return mode;
	}

	public String getEntryPathString() {
		return TreeWalk.pathOf(this);
	}

	public byte[] getEntryPathBuffer() {
		return path;
	}

	public int getEntryPathLength() {
		return pathLen;
	}

	public int getEntryPathHashCode() {
		int hash = 0;
		for (int i = Math.max(0
			byte c = path[i];
			if (c != ' ')
				hash = (hash >>> 2) + (c << 24);
		}
		return hash;
	}

	public abstract byte[] idBuffer();

	public abstract int idOffset();

	public abstract AbstractTreeIterator createSubtreeIterator(
			ObjectReader reader) throws IncorrectObjectTypeException
			IOException;

	public EmptyTreeIterator createEmptyTreeIterator() {
		return new EmptyTreeIterator(this);
	}

	public AbstractTreeIterator createSubtreeIterator(
			final ObjectReader reader
			throws IncorrectObjectTypeException
		return createSubtreeIterator(reader);
	}

	public void reset() throws CorruptObjectException {
		while (!first())
			back(1);
	}

	public abstract boolean first();

	public abstract boolean eof();

	public abstract void next(int delta) throws CorruptObjectException;

	public abstract void back(int delta) throws CorruptObjectException;

	public void skip() throws CorruptObjectException {
		next(1);
	}

	public void stopWalk() {
	}

	protected boolean needsStopWalk() {
		return false;
	}

	public int getNameLength() {
		return pathLen - pathOffset;
	}

	public int getNameOffset() {
		return pathOffset;
	}

	public void getName(byte[] buffer
		System.arraycopy(path
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
	}

	public boolean isWorkTree() {
		return false;
	}
}
