
package org.eclipse.jgit.dircache;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.FileMode.TREE;
import static org.eclipse.jgit.lib.TreeFormatter.entrySize;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.TreeFormatter;
import org.eclipse.jgit.util.MutableInteger;
import org.eclipse.jgit.util.RawParseUtils;

public class DirCacheTree {
	private static final byte[] NO_NAME = {};

	private static final DirCacheTree[] NO_CHILDREN = {};

	private static final Comparator<DirCacheTree> TREE_CMP = new Comparator<DirCacheTree>() {
		@Override
		public int compare(DirCacheTree o1
			final byte[] a = o1.encodedName;
			final byte[] b = o2.encodedName;
			final int aLen = a.length;
			final int bLen = b.length;
			int cPos;
			for (cPos = 0; cPos < aLen && cPos < bLen; cPos++) {
				final int cmp = (a[cPos] & 0xff) - (b[cPos] & 0xff);
				if (cmp != 0)
					return cmp;
			}
			if (aLen == bLen)
				return 0;
			if (aLen < bLen)
				return '/' - (b[cPos] & 0xff);
			return (a[cPos] & 0xff) - '/';
		}
	};

	private DirCacheTree parent;

	byte[] encodedName;

	private int entrySpan;

	private ObjectId id;

	private DirCacheTree[] children;

	private int childCnt;

	DirCacheTree() {
		encodedName = NO_NAME;
		children = NO_CHILDREN;
		childCnt = 0;
		entrySpan = -1;
	}

	private DirCacheTree(final DirCacheTree myParent
			final int pathOff
		parent = myParent;
		encodedName = new byte[pathLen];
		System.arraycopy(path
		children = NO_CHILDREN;
		childCnt = 0;
		entrySpan = -1;
	}

	DirCacheTree(final byte[] in
			final DirCacheTree myParent) {
		parent = myParent;

		int ptr = RawParseUtils.next(in
		final int nameLen = ptr - off.value - 1;
		if (nameLen > 0) {
			encodedName = new byte[nameLen];
			System.arraycopy(in
		} else
			encodedName = NO_NAME;

		entrySpan = RawParseUtils.parseBase10(in
		final int subcnt = RawParseUtils.parseBase10(in
		off.value = RawParseUtils.next(in

		if (entrySpan >= 0) {
			id = ObjectId.fromRaw(in
			off.value += Constants.OBJECT_ID_LENGTH;
		}

		if (subcnt > 0) {
			boolean alreadySorted = true;
			children = new DirCacheTree[subcnt];
			for (int i = 0; i < subcnt; i++) {
				children[i] = new DirCacheTree(in

				if (alreadySorted && i > 0
						&& TREE_CMP.compare(children[i - 1]
					alreadySorted = false;
			}
			if (!alreadySorted)
				Arrays.sort(children
		} else {
			children = NO_CHILDREN;
		}
		childCnt = subcnt;
	}

	void write(byte[] tmp
		int ptr = tmp.length;
		tmp[--ptr] = '\n';
		ptr = RawParseUtils.formatBase10(tmp
		tmp[--ptr] = ' ';
		ptr = RawParseUtils.formatBase10(tmp
		tmp[--ptr] = 0;

		os.write(encodedName);
		os.write(tmp
		if (isValid()) {
			id.copyRawTo(tmp
			os.write(tmp
		}
		for (int i = 0; i < childCnt; i++)
			children[i].write(tmp
	}

	public boolean isValid() {
		return id != null;
	}

	public int getEntrySpan() {
		return entrySpan;
	}

	public int getChildCount() {
		return childCnt;
	}

	public DirCacheTree getChild(int i) {
		return children[i];
	}

	public ObjectId getObjectId() {
		return id;
	}

	public String getNameString() {
		final ByteBuffer bb = ByteBuffer.wrap(encodedName);
		return UTF_8.decode(bb).toString();
	}

	public String getPathString() {
		final StringBuilder r = new StringBuilder();
		appendName(r);
		return r.toString();
	}

	ObjectId writeTree(final DirCacheEntry[] cache
			final int pathOffset
			throws UnmergedPathException
		if (id == null) {
			final int endIdx = cIdx + entrySpan;
			final TreeFormatter fmt = new TreeFormatter(computeSize(cache
					cIdx
			int childIdx = 0;
			int entryIdx = cIdx;

			while (entryIdx < endIdx) {
				final DirCacheEntry e = cache[entryIdx];
				final byte[] ep = e.path;
				if (childIdx < childCnt) {
					final DirCacheTree st = children[childIdx];
					if (st.contains(ep
						fmt.append(st.encodedName
						entryIdx += st.entrySpan;
						childIdx++;
						continue;
					}
				}

				fmt.append(ep
						.getFileMode()
				entryIdx++;
			}

			id = ow.insert(fmt);
		}
		return id;
	}

	private int computeSize(final DirCacheEntry[] cache
			final int pathOffset
			throws UnmergedPathException
		final int endIdx = cIdx + entrySpan;
		int childIdx = 0;
		int entryIdx = cIdx;
		int size = 0;

		while (entryIdx < endIdx) {
			final DirCacheEntry e = cache[entryIdx];
			if (e.getStage() != 0)
				throw new UnmergedPathException(e);

			final byte[] ep = e.path;
			if (childIdx < childCnt) {
				final DirCacheTree st = children[childIdx];
				if (st.contains(ep
					final int stOffset = pathOffset + st.nameLength() + 1;
					st.writeTree(cache

					size += entrySize(TREE

					entryIdx += st.entrySpan;
					childIdx++;
					continue;
				}
			}

			size += entrySize(e.getFileMode()
			entryIdx++;
		}

		return size;
	}

	private void appendName(StringBuilder r) {
		if (parent != null) {
			parent.appendName(r);
			r.append(getNameString());
			r.append('/');
		} else if (nameLength() > 0) {
			r.append(getNameString());
			r.append('/');
		}
	}

	final int nameLength() {
		return encodedName.length;
	}

	final boolean contains(byte[] a
		final byte[] e = encodedName;
		final int eLen = e.length;
		for (int eOff = 0; eOff < eLen && aOff < aLen; eOff++
			if (e[eOff] != a[aOff])
				return false;
		if (aOff >= aLen)
			return false;
		return a[aOff] == '/';
	}

	void validate(final DirCacheEntry[] cache
			final int pathOff) {
		if (entrySpan >= 0 && cIdx + entrySpan <= cCnt) {
			return;
		}

		entrySpan = 0;
		if (cCnt == 0) {
			return;
		}

		final byte[] firstPath = cache[cIdx].path;
		int stIdx = 0;
		while (cIdx < cCnt) {
			final byte[] currPath = cache[cIdx].path;
			if (pathOff > 0 && !peq(firstPath
				break;
			}

			DirCacheTree st = stIdx < childCnt ? children[stIdx] : null;
			final int cc = namecmp(currPath
			if (cc > 0) {
				removeChild(stIdx);
				continue;
			}

			if (cc < 0) {
				final int p = slash(currPath
				if (p < 0) {
					cIdx++;
					entrySpan++;
					continue;
				}

				st = new DirCacheTree(this
				insertChild(stIdx
			}

			assert(st != null);
			st.validate(cache
			cIdx += st.entrySpan;
			entrySpan += st.entrySpan;
			stIdx++;
		}

		while (stIdx < childCnt)
			removeChild(childCnt - 1);
	}

	private void insertChild(int stIdx
		final DirCacheTree[] c = children;
		if (childCnt + 1 <= c.length) {
			if (stIdx < childCnt)
				System.arraycopy(c
			c[stIdx] = st;
			childCnt++;
			return;
		}

		final int n = c.length;
		final DirCacheTree[] a = new DirCacheTree[n + 1];
		if (stIdx > 0)
			System.arraycopy(c
		a[stIdx] = st;
		if (stIdx < n)
			System.arraycopy(c
		children = a;
		childCnt++;
	}

	private void removeChild(int stIdx) {
		final int n = --childCnt;
		if (stIdx < n)
			System.arraycopy(children
		children[n] = null;
	}

	static boolean peq(byte[] a
		if (b.length < aLen)
			return false;
		for (aLen--; aLen >= 0; aLen--)
			if (a[aLen] != b[aLen])
				return false;
		return true;
	}

	private static int namecmp(byte[] a
		if (ct == null)
			return -1;
		final byte[] b = ct.encodedName;
		final int aLen = a.length;
		final int bLen = b.length;
		int bPos = 0;
		for (; aPos < aLen && bPos < bLen; aPos++
			final int cmp = (a[aPos] & 0xff) - (b[bPos] & 0xff);
			if (cmp != 0)
				return cmp;
		}
		if (bPos == bLen)
			return a[aPos] == '/' ? 0 : -1;
		return aLen - bLen;
	}

	private static int slash(byte[] a
		final int aLen = a.length;
		for (; aPos < aLen; aPos++)
			if (a[aPos] == '/')
				return aPos;
		return -1;
	}

	@Override
	public String toString() {
		return getNameString();
	}
}
