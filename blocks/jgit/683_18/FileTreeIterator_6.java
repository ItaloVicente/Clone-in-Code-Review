
package org.eclipse.jgit.treewalk;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.WindowCursor;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public abstract class AbstractTreeIterator {
	protected static final int DEFAULT_PATH_SIZE = 128;

	protected static final byte[] zeroid = new byte[Constants.OBJECT_ID_LENGTH];

	final AbstractTreeIterator parent;

	AbstractTreeIterator matches;

	int matchShift;

	protected int mode;

	protected byte[] path;

	protected final int pathOffset;

	protected int pathLen;

	protected long gitIgnoreTimeStamp;

	protected AbstractTreeIterator() {
		parent = null;
		path = new byte[DEFAULT_PATH_SIZE];
		pathOffset = 0;
		gitIgnoreTimeStamp = 0l;
	}

	protected AbstractTreeIterator(final String prefix) {
		parent = null;
		gitIgnoreTimeStamp = 0l;

		if (prefix != null && prefix.length() > 0) {
			final ByteBuffer b;

			b = Constants.CHARSET.encode(CharBuffer.wrap(prefix));
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

	protected AbstractTreeIterator(final byte[] prefix) {
		parent = null;
		gitIgnoreTimeStamp = 0l;

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

	protected AbstractTreeIterator(final AbstractTreeIterator p) {
		parent = p;
		path = p.path;
		pathOffset = p.pathLen + 1;
		gitIgnoreTimeStamp = 0l;

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
		gitIgnoreTimeStamp = 0l;
	}

	protected void growPath(final int len) {
		setPathCapacity(path.length << 1
	}

	protected void ensurePathCapacity(final int capacity
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

	public int pathCompare(final AbstractTreeIterator p) {
		return pathCompare(p
	}

	int pathCompare(final AbstractTreeIterator p
		final byte[] a = path;
		final byte[] b = p.path;
		final int aLen = pathLen;
		final int bLen = p.pathLen;
		int cPos;

		cPos = alreadyMatch(this

		for (; cPos < aLen && cPos < bLen; cPos++) {
			final int cmp = (a[cPos] & 0xff) - (b[cPos] & 0xff);
			if (cmp != 0)
				return cmp;
		}

		if (cPos < aLen)
			return (a[cPos] & 0xff) - lastPathChar(pMode);
		if (cPos < bLen)
			return lastPathChar(mode) - (b[cPos] & 0xff);
		return lastPathChar(mode) - lastPathChar(pMode);
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

	private static int lastPathChar(final int mode) {
		return FileMode.TREE.equals(mode) ? '/' : '\0';
	}

	public boolean idEqual(final AbstractTreeIterator otherIterator) {
		return ObjectId.equals(idBuffer()
				otherIterator.idBuffer()
	}

	public ObjectId getEntryObjectId() {
		return ObjectId.fromRaw(idBuffer()
	}

	public void getEntryObjectId(final MutableObjectId out) {
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

	public abstract byte[] idBuffer();

	public abstract int idOffset();

	public abstract AbstractTreeIterator createSubtreeIterator(Repository repo)
			throws IncorrectObjectTypeException

	public EmptyTreeIterator createEmptyTreeIterator() {
		return new EmptyTreeIterator(this);
	}

	public AbstractTreeIterator createSubtreeIterator(final Repository repo
			final MutableObjectId idBuffer
			throws IncorrectObjectTypeException
		return createSubtreeIterator(repo);
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

	public int getNameLength() {
		return pathLen - pathOffset;
	}

	public void getName(byte[] buffer
		System.arraycopy(path
	}

	public boolean hasGitIgnore() {
		return gitIgnoreTimeStamp > 0;
	}

	public long getGitIgnoreLastModified() {
		return gitIgnoreTimeStamp;
	}
}
