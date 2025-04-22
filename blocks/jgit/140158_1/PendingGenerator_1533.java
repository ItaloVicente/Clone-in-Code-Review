
package org.eclipse.jgit.revwalk;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.filter.ObjectFilter;
import org.eclipse.jgit.util.RawParseUtils;

public class ObjectWalk extends RevWalk {
	private static final int ID_SZ = 20;
	private static final int TYPE_SHIFT = 12;
	private static final int TYPE_TREE = 0040000 >>> TYPE_SHIFT;
	private static final int TYPE_SYMLINK = 0120000 >>> TYPE_SHIFT;
	private static final int TYPE_FILE = 0100000 >>> TYPE_SHIFT;
	private static final int TYPE_GITLINK = 0160000 >>> TYPE_SHIFT;

	private static final int IN_PENDING = RevWalk.REWRITE;

	private List<RevObject> rootObjects;

	private BlockObjQueue pendingObjects;

	private ObjectFilter objectFilter;

	private TreeVisit freeVisit;

	private TreeVisit currVisit;

	private byte[] pathBuf;

	private int pathLen;

	private boolean boundary;

	public ObjectWalk(Repository repo) {
		this(repo.newObjectReader());
	}

	public ObjectWalk(ObjectReader or) {
		super(or);
		setRetainBody(false);
		rootObjects = new ArrayList<>();
		pendingObjects = new BlockObjQueue();
		objectFilter = ObjectFilter.ALL;
		pathBuf = new byte[256];
	}

	public void markStart(RevObject o) throws MissingObjectException
			IncorrectObjectTypeException
		while (o instanceof RevTag) {
			addObject(o);
			o = ((RevTag) o).getObject();
			parseHeaders(o);
		}

		if (o instanceof RevCommit)
			super.markStart((RevCommit) o);
		else
			addObject(o);
	}

	public void markUninteresting(RevObject o) throws MissingObjectException
			IncorrectObjectTypeException
		while (o instanceof RevTag) {
			o.flags |= UNINTERESTING;
			if (boundary)
				addObject(o);
			o = ((RevTag) o).getObject();
			parseHeaders(o);
		}

		if (o instanceof RevCommit)
			super.markUninteresting((RevCommit) o);
		else if (o instanceof RevTree)
			markTreeUninteresting((RevTree) o);
		else
			o.flags |= UNINTERESTING;

		if (o.getType() != OBJ_COMMIT && boundary)
			addObject(o);
	}

	@Override
	public void sort(RevSort s) {
		super.sort(s);
		boundary = hasRevSort(RevSort.BOUNDARY);
	}

	@Override
	public void sort(RevSort s
		super.sort(s
		boundary = hasRevSort(RevSort.BOUNDARY);
	}

	public ObjectFilter getObjectFilter() {
		return objectFilter;
	}

	public void setObjectFilter(ObjectFilter newFilter) {
		assertNotStarted();
		objectFilter = newFilter != null ? newFilter : ObjectFilter.ALL;
	}

	@Override
	public RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		for (;;) {
			final RevCommit r = super.next();
			if (r == null) {
				return null;
			}
			final RevTree t = r.getTree();
			if ((r.flags & UNINTERESTING) != 0) {
				if (objectFilter.include(this
					markTreeUninteresting(t);
				}
				if (boundary) {
					return r;
				}
				continue;
			}
			if (objectFilter.include(this
				pendingObjects.add(t);
			}
			return r;
		}
	}

	public RevObject nextObject() throws MissingObjectException
			IncorrectObjectTypeException
		pathLen = 0;

		TreeVisit tv = currVisit;
		while (tv != null) {
			byte[] buf = tv.buf;
			for (int ptr = tv.ptr; ptr < buf.length;) {
				int startPtr = ptr;
				ptr = findObjectId(buf
				idBuffer.fromRaw(buf
				ptr += ID_SZ;

				if (!objectFilter.include(this
					continue;
				}

				RevObject obj = objects.get(idBuffer);
				if (obj != null && (obj.flags & SEEN) != 0)
					continue;

				int mode = parseMode(buf
				int flags;
				switch (mode >>> TYPE_SHIFT) {
				case TYPE_FILE:
				case TYPE_SYMLINK:
					if (obj == null) {
						obj = new RevBlob(idBuffer);
						obj.flags = SEEN;
						objects.add(obj);
						return obj;
					}
					if (!(obj instanceof RevBlob))
						throw new IncorrectObjectTypeException(obj
					obj.flags = flags = obj.flags | SEEN;
					if ((flags & UNINTERESTING) == 0)
						return obj;
					if (boundary)
						return obj;
					continue;

				case TYPE_TREE:
					if (obj == null) {
						obj = new RevTree(idBuffer);
						obj.flags = SEEN;
						objects.add(obj);
						return pushTree(obj);
					}
					if (!(obj instanceof RevTree))
						throw new IncorrectObjectTypeException(obj
					obj.flags = flags = obj.flags | SEEN;
					if ((flags & UNINTERESTING) == 0)
						return pushTree(obj);
					if (boundary)
						return pushTree(obj);
					continue;

				case TYPE_GITLINK:
					continue;

				default:
					throw new CorruptObjectException(MessageFormat.format(
							JGitText.get().corruptObjectInvalidMode3
							String.format("%o"
							idBuffer.name()
							RawParseUtils.decode(buf
							tv.obj));
				}
			}

			currVisit = tv.parent;
			releaseTreeVisit(tv);
			tv = currVisit;
		}

		for (;;) {
			RevObject o = pendingObjects.next();
			if (o == null) {
				return null;
			}
			int flags = o.flags;
			if ((flags & SEEN) != 0)
				continue;
			flags |= SEEN;
			o.flags = flags;
			if ((flags & UNINTERESTING) == 0 | boundary) {
				if (o instanceof RevTree) {
					assert currVisit == null;

					pushTree(o);
				}
				return o;
			}
		}
	}

	private static int findObjectId(byte[] buf
		for (;;) {
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;

			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;

			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;

			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
		}
	}

	private static int parseMode(byte[] buf
		int mode = buf[startPtr] - '0';
		for (;;) {
			byte c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';

			c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';

			c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';

			c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';

			c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';

			c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';

			c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';
		}

		tv.ptr = recEndPtr;
		tv.namePtr = startPtr + 1;
		tv.nameEnd = recEndPtr - (ID_SZ + 1);
		return mode;
	}

	public void checkConnectivity() throws MissingObjectException
			IncorrectObjectTypeException
		for (;;) {
			final RevCommit c = next();
			if (c == null)
				break;
		}
		for (;;) {
			final RevObject o = nextObject();
			if (o == null)
				break;
			if (o instanceof RevBlob && !reader.has(o))
				throw new MissingObjectException(o
		}
	}

	public String getPathString() {
		if (pathLen == 0) {
			pathLen = updatePathBuf(currVisit);
			if (pathLen == 0)
				return null;
		}
		return RawParseUtils.decode(pathBuf
	}

	public int getPathHashCode() {
		TreeVisit tv = currVisit;
		if (tv == null)
			return 0;

		int nameEnd = tv.nameEnd;
		if (nameEnd == 0) {
			tv = tv.parent;
			if (tv == null)
				return 0;
			nameEnd = tv.nameEnd;
		}

		byte[] buf;
		int ptr;

		if (16 <= (nameEnd - tv.namePtr)) {
			buf = tv.buf;
			ptr = nameEnd - 16;
		} else {
			nameEnd = pathLen;
			if (nameEnd == 0) {
				nameEnd = updatePathBuf(currVisit);
				pathLen = nameEnd;
			}
			buf = pathBuf;
			ptr = Math.max(0
		}

		int hash = 0;
		for (; ptr < nameEnd; ptr++) {
			byte c = buf[ptr];
			if (c != ' ')
				hash = (hash >>> 2) + (c << 24);
		}
		return hash;
	}

	public byte[] getPathBuffer() {
		if (pathLen == 0)
			pathLen = updatePathBuf(currVisit);
		return pathBuf;
	}

	public int getPathLength() {
		if (pathLen == 0)
			pathLen = updatePathBuf(currVisit);
		return pathLen;
	}

	private int updatePathBuf(TreeVisit tv) {
		if (tv == null)
			return 0;

		int nameEnd = tv.nameEnd;
		if (nameEnd == 0)
			return updatePathBuf(tv.parent);

		int ptr = tv.pathLen;
		if (ptr == 0) {
			ptr = updatePathBuf(tv.parent);
			if (ptr == pathBuf.length)
				growPathBuf(ptr);
			if (ptr != 0)
				pathBuf[ptr++] = '/';
			tv.pathLen = ptr;
		}

		int namePtr = tv.namePtr;
		int nameLen = nameEnd - namePtr;
		int end = ptr + nameLen;
		while (pathBuf.length < end)
			growPathBuf(ptr);
		System.arraycopy(tv.buf
		return end;
	}

	private void growPathBuf(int ptr) {
		byte[] newBuf = new byte[pathBuf.length << 1];
		System.arraycopy(pathBuf
		pathBuf = newBuf;
	}

	@Override
	public void dispose() {
		super.dispose();
		pendingObjects = new BlockObjQueue();
		currVisit = null;
		freeVisit = null;
	}

	@Override
	protected void reset(int retainFlags) {
		super.reset(retainFlags);

		for (RevObject obj : rootObjects)
			obj.flags &= ~IN_PENDING;

		rootObjects = new ArrayList<>();
		pendingObjects = new BlockObjQueue();
		currVisit = null;
		freeVisit = null;
	}

	private void addObject(RevObject o) {
		if ((o.flags & IN_PENDING) == 0) {
			o.flags |= IN_PENDING;
			rootObjects.add(o);
			pendingObjects.add(o);
		}
	}

	private void markTreeUninteresting(RevTree tree)
			throws MissingObjectException
			IOException {
		if ((tree.flags & UNINTERESTING) != 0)
			return;
		tree.flags |= UNINTERESTING;

		byte[] raw = reader.open(tree
		for (int ptr = 0; ptr < raw.length;) {
			byte c = raw[ptr];
			int mode = c - '0';
			for (;;) {
				c = raw[++ptr];
				if (' ' == c)
					break;
				mode <<= 3;
				mode += c - '0';
			}
			while (raw[++ptr] != 0) {
			}

			switch (mode >>> TYPE_SHIFT) {
			case TYPE_FILE:
			case TYPE_SYMLINK:
				idBuffer.fromRaw(raw
				lookupBlob(idBuffer).flags |= UNINTERESTING;
				break;

			case TYPE_TREE:
				idBuffer.fromRaw(raw
				markTreeUninteresting(lookupTree(idBuffer));
				break;

			case TYPE_GITLINK:
				break;

			default:
				idBuffer.fromRaw(raw
				throw new CorruptObjectException(MessageFormat.format(
						JGitText.get().corruptObjectInvalidMode3
						String.format("%o"
						idBuffer.name()
			}
			ptr += ID_SZ;
		}
	}

	private RevObject pushTree(RevObject obj) throws LargeObjectException
			MissingObjectException
		TreeVisit tv = freeVisit;
		if (tv != null) {
			freeVisit = tv.parent;
			tv.ptr = 0;
			tv.namePtr = 0;
			tv.nameEnd = 0;
			tv.pathLen = 0;
		} else {
			tv = new TreeVisit();
		}
		tv.obj = obj;
		tv.buf = reader.open(obj
		tv.parent = currVisit;
		currVisit = tv;

		return obj;
	}

	private void releaseTreeVisit(TreeVisit tv) {
		tv.buf = null;
		tv.parent = freeVisit;
		freeVisit = tv;
	}

	private static class TreeVisit {
		TreeVisit parent;

		RevObject obj;

		byte[] buf;

		int ptr;

		int namePtr;

		int nameEnd;

		int pathLen;
	}
}
