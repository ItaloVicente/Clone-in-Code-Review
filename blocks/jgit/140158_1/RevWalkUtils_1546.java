
package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevWalkException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.AsyncObjectLoaderQueue;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class RevWalk implements Iterable<RevCommit>
	private static final int MB = 1 << 20;

	static final int PARSED = 1 << 0;

	static final int SEEN = 1 << 1;

	static final int UNINTERESTING = 1 << 2;

	static final int REWRITE = 1 << 3;

	static final int TEMP_MARK = 1 << 4;

	static final int TOPO_DELAY = 1 << 5;

	static final int RESERVED_FLAGS = 6;

	private static final int APP_FLAGS = -1 & ~((1 << RESERVED_FLAGS) - 1);

	final ObjectReader reader;

	private final boolean closeReader;

	final MutableObjectId idBuffer;

	ObjectIdOwnerMap<RevObject> objects;

	int freeFlags = APP_FLAGS;

	private int delayFreeFlags;

	private int retainOnReset;

	int carryFlags = UNINTERESTING;

	final ArrayList<RevCommit> roots;

	AbstractRevQueue queue;

	Generator pending;

	private final EnumSet<RevSort> sorting;

	private RevFilter filter;

	private TreeFilter treeFilter;

	private boolean retainBody = true;

	private boolean rewriteParents = true;

	boolean shallowCommitsInitialized;

	public RevWalk(Repository repo) {
		this(repo.newObjectReader()
	}

	public RevWalk(ObjectReader or) {
		this(or
	}

	private RevWalk(ObjectReader or
		reader = or;
		idBuffer = new MutableObjectId();
		objects = new ObjectIdOwnerMap<>();
		roots = new ArrayList<>();
		queue = new DateRevQueue();
		pending = new StartGenerator(this);
		sorting = EnumSet.of(RevSort.NONE);
		filter = RevFilter.ALL;
		treeFilter = TreeFilter.ALL;
		this.closeReader = closeReader;
	}

	public ObjectReader getObjectReader() {
		return reader;
	}

	@Override
	public void close() {
		if (closeReader) {
			reader.close();
		}
	}

	public void markStart(RevCommit c) throws MissingObjectException
			IncorrectObjectTypeException
		if ((c.flags & SEEN) != 0)
			return;
		if ((c.flags & PARSED) == 0)
			c.parseHeaders(this);
		c.flags |= SEEN;
		roots.add(c);
		queue.add(c);
	}

	public void markStart(Collection<RevCommit> list)
			throws MissingObjectException
			IOException {
		for (RevCommit c : list)
			markStart(c);
	}

	public void markUninteresting(RevCommit c)
			throws MissingObjectException
			IOException {
		c.flags |= UNINTERESTING;
		carryFlagsImpl(c);
		markStart(c);
	}

	public boolean isMergedInto(RevCommit base
			throws MissingObjectException
			IOException {
		final RevFilter oldRF = filter;
		final TreeFilter oldTF = treeFilter;
		try {
			finishDelayedFreeFlags();
			reset(~freeFlags & APP_FLAGS);
			filter = RevFilter.MERGE_BASE;
			treeFilter = TreeFilter.ALL;
			markStart(tip);
			markStart(base);
			RevCommit mergeBase;
			while ((mergeBase = next()) != null)
				if (mergeBase == base)
					return true;
			return false;
		} finally {
			filter = oldRF;
			treeFilter = oldTF;
		}
	}

	public RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		return pending.next();
	}

	public EnumSet<RevSort> getRevSort() {
		return sorting.clone();
	}

	public boolean hasRevSort(RevSort sort) {
		return sorting.contains(sort);
	}

	public void sort(RevSort s) {
		assertNotStarted();
		sorting.clear();
		sorting.add(s);
	}

	public void sort(RevSort s
		assertNotStarted();
		if (use)
			sorting.add(s);
		else
			sorting.remove(s);

		if (sorting.size() > 1)
			sorting.remove(RevSort.NONE);
		else if (sorting.isEmpty())
			sorting.add(RevSort.NONE);
	}

	@NonNull
	public RevFilter getRevFilter() {
		return filter;
	}

	public void setRevFilter(RevFilter newFilter) {
		assertNotStarted();
		filter = newFilter != null ? newFilter : RevFilter.ALL;
	}

	@NonNull
	public TreeFilter getTreeFilter() {
		return treeFilter;
	}

	public void setTreeFilter(TreeFilter newFilter) {
		assertNotStarted();
		treeFilter = newFilter != null ? newFilter : TreeFilter.ALL;
	}

	public void setRewriteParents(boolean rewrite) {
		rewriteParents = rewrite;
	}

	boolean getRewriteParents() {
		return rewriteParents;
	}

	public boolean isRetainBody() {
		return retainBody;
	}

	public void setRetainBody(boolean retain) {
		retainBody = retain;
	}

	@NonNull
	public RevBlob lookupBlob(AnyObjectId id) {
		RevBlob c = (RevBlob) objects.get(id);
		if (c == null) {
			c = new RevBlob(id);
			objects.add(c);
		}
		return c;
	}

	@NonNull
	public RevTree lookupTree(AnyObjectId id) {
		RevTree c = (RevTree) objects.get(id);
		if (c == null) {
			c = new RevTree(id);
			objects.add(c);
		}
		return c;
	}

	@NonNull
	public RevCommit lookupCommit(AnyObjectId id) {
		RevCommit c = (RevCommit) objects.get(id);
		if (c == null) {
			c = createCommit(id);
			objects.add(c);
		}
		return c;
	}

	@NonNull
	public RevTag lookupTag(AnyObjectId id) {
		RevTag c = (RevTag) objects.get(id);
		if (c == null) {
			c = new RevTag(id);
			objects.add(c);
		}
		return c;
	}

	@NonNull
	public RevObject lookupAny(AnyObjectId id
		RevObject r = objects.get(id);
		if (r == null) {
			switch (type) {
			case Constants.OBJ_COMMIT:
				r = createCommit(id);
				break;
			case Constants.OBJ_TREE:
				r = new RevTree(id);
				break;
			case Constants.OBJ_BLOB:
				r = new RevBlob(id);
				break;
			case Constants.OBJ_TAG:
				r = new RevTag(id);
				break;
			default:
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().invalidGitType
			}
			objects.add(r);
		}
		return r;
	}

	public RevObject lookupOrNull(AnyObjectId id) {
		return objects.get(id);
	}

	@NonNull
	public RevCommit parseCommit(AnyObjectId id)
			throws MissingObjectException
			IOException {
		RevObject c = peel(parseAny(id));
		if (!(c instanceof RevCommit))
			throw new IncorrectObjectTypeException(id.toObjectId()
					Constants.TYPE_COMMIT);
		return (RevCommit) c;
	}

	@NonNull
	public RevTree parseTree(AnyObjectId id)
			throws MissingObjectException
			IOException {
		RevObject c = peel(parseAny(id));

		final RevTree t;
		if (c instanceof RevCommit)
			t = ((RevCommit) c).getTree();
		else if (!(c instanceof RevTree))
			throw new IncorrectObjectTypeException(id.toObjectId()
					Constants.TYPE_TREE);
		else
			t = (RevTree) c;
		parseHeaders(t);
		return t;
	}

	@NonNull
	public RevTag parseTag(AnyObjectId id) throws MissingObjectException
			IncorrectObjectTypeException
		RevObject c = parseAny(id);
		if (!(c instanceof RevTag))
			throw new IncorrectObjectTypeException(id.toObjectId()
					Constants.TYPE_TAG);
		return (RevTag) c;
	}

	@NonNull
	public RevObject parseAny(AnyObjectId id)
			throws MissingObjectException
		RevObject r = objects.get(id);
		if (r == null)
			r = parseNew(id
		else
			parseHeaders(r);
		return r;
	}

	private RevObject parseNew(AnyObjectId id
			throws LargeObjectException
			MissingObjectException
		RevObject r;
		int type = ldr.getType();
		switch (type) {
		case Constants.OBJ_COMMIT: {
			final RevCommit c = createCommit(id);
			c.parseCanonical(this
			r = c;
			break;
		}
		case Constants.OBJ_TREE: {
			r = new RevTree(id);
			r.flags |= PARSED;
			break;
		}
		case Constants.OBJ_BLOB: {
			r = new RevBlob(id);
			r.flags |= PARSED;
			break;
		}
		case Constants.OBJ_TAG: {
			final RevTag t = new RevTag(id);
			t.parseCanonical(this
			r = t;
			break;
		}
		default:
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().badObjectType
		}
		objects.add(r);
		return r;
	}

	byte[] getCachedBytes(RevObject obj) throws LargeObjectException
			MissingObjectException
		return getCachedBytes(obj
	}

	byte[] getCachedBytes(RevObject obj
			throws LargeObjectException
		try {
			return ldr.getCachedBytes(5 * MB);
		} catch (LargeObjectException tooBig) {
			tooBig.setObjectId(obj);
			throw tooBig;
		}
	}

	public <T extends ObjectId> AsyncRevObjectQueue parseAny(
			Iterable<T> objectIds
		List<T> need = new ArrayList<>();
		List<RevObject> have = new ArrayList<>();
		for (T id : objectIds) {
			RevObject r = objects.get(id);
			if (r != null && (r.flags & PARSED) != 0)
				have.add(r);
			else
				need.add(id);
		}

		final Iterator<RevObject> objItr = have.iterator();
		if (need.isEmpty()) {
			return new AsyncRevObjectQueue() {
				@Override
				public RevObject next() {
					return objItr.hasNext() ? objItr.next() : null;
				}

				@Override
				public boolean cancel(boolean mayInterruptIfRunning) {
					return true;
				}

				@Override
				public void release() {
				}
			};
		}

		final AsyncObjectLoaderQueue<T> lItr = reader.open(need
		return new AsyncRevObjectQueue() {
			@Override
			public RevObject next() throws MissingObjectException
					IncorrectObjectTypeException
				if (objItr.hasNext())
					return objItr.next();
				if (!lItr.next())
					return null;

				ObjectId id = lItr.getObjectId();
				ObjectLoader ldr = lItr.open();
				RevObject r = objects.get(id);
				if (r == null)
					r = parseNew(id
				else if (r instanceof RevCommit) {
					byte[] raw = ldr.getCachedBytes();
					((RevCommit) r).parseCanonical(RevWalk.this
				} else if (r instanceof RevTag) {
					byte[] raw = ldr.getCachedBytes();
					((RevTag) r).parseCanonical(RevWalk.this
				} else
					r.flags |= PARSED;
				return r;
			}

			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				return lItr.cancel(mayInterruptIfRunning);
			}

			@Override
			public void release() {
				lItr.release();
			}
		};
	}

	public void parseHeaders(RevObject obj)
			throws MissingObjectException
		if ((obj.flags & PARSED) == 0)
			obj.parseHeaders(this);
	}

	public void parseBody(RevObject obj)
			throws MissingObjectException
		obj.parseBody(this);
	}

	public RevObject peel(RevObject obj) throws MissingObjectException
			IOException {
		while (obj instanceof RevTag) {
			parseHeaders(obj);
			obj = ((RevTag) obj).getObject();
		}
		parseHeaders(obj);
		return obj;
	}

	public RevFlag newFlag(String name) {
		final int m = allocFlag();
		return new RevFlag(this
	}

	int allocFlag() {
		if (freeFlags == 0)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().flagsAlreadyCreated
					Integer.valueOf(32 - RESERVED_FLAGS)));
		final int m = Integer.lowestOneBit(freeFlags);
		freeFlags &= ~m;
		return m;
	}

	public void carry(RevFlag flag) {
		if ((freeFlags & flag.mask) != 0)
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().flagIsDisposed
		if (flag.walker != this)
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().flagNotFromThis
		carryFlags |= flag.mask;
	}

	public void carry(Collection<RevFlag> set) {
		for (RevFlag flag : set)
			carry(flag);
	}

	public final void retainOnReset(RevFlag flag) {
		if ((freeFlags & flag.mask) != 0)
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().flagIsDisposed
		if (flag.walker != this)
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().flagNotFromThis
		retainOnReset |= flag.mask;
	}

	public final void retainOnReset(Collection<RevFlag> flags) {
		for (RevFlag f : flags)
			retainOnReset(f);
	}

	public void disposeFlag(RevFlag flag) {
		freeFlag(flag.mask);
	}

	void freeFlag(int mask) {
		retainOnReset &= ~mask;
		if (isNotStarted()) {
			freeFlags |= mask;
			carryFlags &= ~mask;
		} else {
			delayFreeFlags |= mask;
		}
	}

	private void finishDelayedFreeFlags() {
		if (delayFreeFlags != 0) {
			freeFlags |= delayFreeFlags;
			carryFlags &= ~delayFreeFlags;
			delayFreeFlags = 0;
		}
	}

	public final void reset() {
		reset(0);
	}

	public final void resetRetain(RevFlagSet retainFlags) {
		reset(retainFlags.mask);
	}

	public final void resetRetain(RevFlag... retainFlags) {
		int mask = 0;
		for (RevFlag flag : retainFlags)
			mask |= flag.mask;
		reset(mask);
	}

	protected void reset(int retainFlags) {
		finishDelayedFreeFlags();
		retainFlags |= PARSED | retainOnReset;
		final int clearFlags = ~retainFlags;

		final FIFORevQueue q = new FIFORevQueue();
		for (RevCommit c : roots) {
			if ((c.flags & clearFlags) == 0)
				continue;
			c.flags &= retainFlags;
			c.reset();
			q.add(c);
		}

		for (;;) {
			final RevCommit c = q.next();
			if (c == null)
				break;
			if (c.parents == null)
				continue;
			for (RevCommit p : c.parents) {
				if ((p.flags & clearFlags) == 0)
					continue;
				p.flags &= retainFlags;
				p.reset();
				q.add(p);
			}
		}

		roots.clear();
		queue = new DateRevQueue();
		pending = new StartGenerator(this);
	}

	public void dispose() {
		reader.close();
		freeFlags = APP_FLAGS;
		delayFreeFlags = 0;
		retainOnReset = 0;
		carryFlags = UNINTERESTING;
		objects.clear();
		roots.clear();
		queue = new DateRevQueue();
		pending = new StartGenerator(this);
		shallowCommitsInitialized = false;
	}

	@Nullable
	private RevCommit nextForIterator() {
		try {
			return next();
		} catch (IOException e) {
			throw new RevWalkException(e);
		}
	}

	@Override
	public Iterator<RevCommit> iterator() {
		RevCommit first = nextForIterator();

		return new Iterator<RevCommit>() {
			RevCommit next = first;

			@Override
			public boolean hasNext() {
				return next != null;
			}

			@Override
			public RevCommit next() {
				RevCommit r = next;
				next = nextForIterator();
				return r;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	protected void assertNotStarted() {
		if (isNotStarted())
			return;
		throw new IllegalStateException(JGitText.get().outputHasAlreadyBeenStarted);
	}

	private boolean isNotStarted() {
		return pending instanceof StartGenerator;
	}

	public ObjectWalk toObjectWalkWithSameObjects() {
		ObjectWalk ow = new ObjectWalk(reader);
		RevWalk rw = ow;
		rw.objects = objects;
		rw.freeFlags = freeFlags;
		return ow;
	}

	protected RevCommit createCommit(AnyObjectId id) {
		return new RevCommit(id);
	}

	void carryFlagsImpl(RevCommit c) {
		final int carry = c.flags & carryFlags;
		if (carry != 0)
			RevCommit.carryFlags(c
	}

	public void assumeShallow(Collection<? extends ObjectId> ids) {
		for (ObjectId id : ids)
			lookupCommit(id).parents = RevCommit.NO_PARENTS;
	}

	void initializeShallowCommits(RevCommit rc) throws IOException {
		if (shallowCommitsInitialized) {
			throw new IllegalStateException(
					JGitText.get().shallowCommitsAlreadyInitialized);
		}

		shallowCommitsInitialized = true;

		if (reader == null) {
			return;
		}

		for (ObjectId id : reader.getShallowCommits()) {
			if (id.equals(rc.getId())) {
				rc.parents = RevCommit.NO_PARENTS;
			} else {
				lookupCommit(id).parents = RevCommit.NO_PARENTS;
			}
		}
	}
}
