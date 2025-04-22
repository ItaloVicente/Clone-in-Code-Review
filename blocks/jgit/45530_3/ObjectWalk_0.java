package org.eclipse.jgit.revwalk;

import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

class ObjectModeCache {
	private static final int CACHE_INITIAL_SIZE = 100003;

	public static final ObjectModeCache NOOP_CACHE = new ObjectModeCache() {
		private final ChildList noopList = new ChildList() {

			@Override
			public Iterator<CachedObject> iterator() {
				return Collections.emptyIterator();
			}

			@Override
			public void add(CachedObject child) {
			}

			@Override
			public CachedObject[] toArray() {
				return null;
			}

			@Override
			public int size() {
				return 0;
			}

		};

		private final CachedObject noopObject = new CachedObject(
				ObjectId.zeroId()

			@Override
			public CachedObject[] getChildren() {
				return null;
			}

			@Override
			public boolean hasSetChildren() {
				return false;
			}
		};

		@Override
		void prune() {
		}

		@Override
		CachedObject get(AnyObjectId id
			return noopObject;
		}

		@Override
		void addRoot(RevTree tree) {
		}

		@Override
		void setChildren(CachedObject parent
		}

		@Override
		public ChildList getChildList() {
			return noopList;
		}
	};

	public class CachedObject extends ObjectId {

		public final int mode;

		private int parentCount;

		private CachedObject[] children;

		private CachedObject(AnyObjectId objectId
			super(objectId);
			this.mode = mode;
			parentCount = 0;
		}

		public CachedObject[] getChildren() {
			return this.children;
		}

		public boolean hasSetChildren() {
			return children != null;
		}
	}

	interface ChildList extends Iterable<CachedObject> {
		void add(CachedObject child);

		CachedObject[] toArray();

		int size();

		public static class Impl implements ChildList {
			List<CachedObject> children = new LinkedList<CachedObject>();

			@Override
			public Iterator<CachedObject> iterator() {
				return children.iterator();
			}

			@Override
			public void add(CachedObject child) {
				children.add(child);

			}

			@Override
			public CachedObject[] toArray() {
				return children.toArray(new CachedObject[size()]);
			}

			@Override
			public int size() {
				return children.size();
			}
		}
	}

	private int maxSize;

	private LinkedList<RevTree> rootsQueue;

	private Set<RevTree> rootsSet;

	private Hashtable<ObjectId

	ObjectModeCache() {
	}

	public ObjectModeCache(int maxNbrOfObjects) {
		rootsQueue = new LinkedList<RevTree>();
		rootsSet = new HashSet<RevTree>();
		cachedObjects = new Hashtable<ObjectId
				CACHE_INITIAL_SIZE);
		maxSize = maxNbrOfObjects;
	}

	CachedObject get(AnyObjectId objectId
		CachedObject obj = cachedObjects.get(objectId);
		if (obj == null) {
			obj = new CachedObject(objectId
			cachedObjects.put(obj
		}
		return obj;
	}

	void prune() {
		if (cachedObjects.size() > maxSize && !rootsQueue.isEmpty()) {
			Iterator<RevTree> itr = rootsQueue.iterator();
			while (itr.hasNext()) {
				RevTree oldestTree = itr.next();
				itr.remove();
				rootsSet.remove(oldestTree);
				CachedObject root = cachedObjects.get(oldestTree);
				if (root.parentCount == 0) {
					removeRec(root);
					return;
				}
			}
		}
	}

	void addRoot(RevTree tree) {
		prune();
		if (!rootsSet.contains(tree)) {
			this.rootsQueue.add(tree);
			this.rootsSet.add(tree);
		}
	}

	void setChildren(CachedObject parent
		for (CachedObject child : children) {
			child.parentCount++;
		}
		parent.children = children.toArray();
	}

	private void removeRec(CachedObject node) {
		cachedObjects.remove(node);
		if (node.hasSetChildren()) {
			for (CachedObject child : node.getChildren()) {
				child.parentCount--;
				if (child.parentCount == 0) {
					removeRec(child);
				}
			}
		}
	}

	public ChildList getChildList() {
		return new ChildList.Impl();
	}
}
