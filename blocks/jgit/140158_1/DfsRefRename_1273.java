
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.lib.Ref.UNDEFINED_UPDATE_INDEX;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.RefList;
import org.eclipse.jgit.util.RefMap;

public abstract class DfsRefDatabase extends RefDatabase {
	private final DfsRepository repository;

	private final AtomicReference<RefCache> cache;

	protected DfsRefDatabase(DfsRepository repository) {
		this.repository = repository;
		this.cache = new AtomicReference<>();
	}

	protected DfsRepository getRepository() {
		return repository;
	}

	boolean exists() throws IOException {
		return 0 < read().size();
	}

	@Override
	public Ref exactRef(String name) throws IOException {
		RefCache curr = read();
		Ref ref = curr.ids.get(name);
		return ref != null ? resolve(ref
	}

	@Override
	public List<Ref> getAdditionalRefs() {
		return Collections.emptyList();
	}

	@Override
	public Map<String
		RefCache curr = read();
		RefList<Ref> packed = RefList.emptyList();
		RefList<Ref> loose = curr.ids;
		RefList.Builder<Ref> sym = new RefList.Builder<>(curr.sym.size());

		for (int idx = 0; idx < curr.sym.size(); idx++) {
			Ref ref = curr.sym.get(idx);
			String name = ref.getName();
			ref = resolve(ref
			if (ref != null && ref.getObjectId() != null) {
				sym.add(ref);
			} else {
				int toRemove = loose.find(name);
				if (0 <= toRemove)
					loose = loose.remove(toRemove);
			}
		}

		return new RefMap(prefix
	}

	private Ref resolve(Ref ref
			throws IOException {
		if (!ref.isSymbolic())
			return ref;

		Ref dst = ref.getTarget();

		if (MAX_SYMBOLIC_REF_DEPTH <= depth)

		dst = loose.get(dst.getName());
		if (dst == null)
			return ref;

		dst = resolve(dst
		if (dst == null)
			return null;
		return new SymbolicRef(ref.getName()
	}

	@Override
	public Ref peel(Ref ref) throws IOException {
		final Ref oldLeaf = ref.getLeaf();
		if (oldLeaf.isPeeled() || oldLeaf.getObjectId() == null)
			return ref;

		Ref newLeaf = doPeel(oldLeaf);

		RefCache cur = read();
		int idx = cur.ids.find(oldLeaf.getName());
		if (0 <= idx && cur.ids.get(idx) == oldLeaf) {
			RefList<Ref> newList = cur.ids.set(idx
			cache.compareAndSet(cur
			cachePeeledState(oldLeaf
		}

		return recreate(ref
	}

	Ref doPeel(Ref leaf) throws MissingObjectException
			IOException {
		try (RevWalk rw = new RevWalk(repository)) {
			RevObject obj = rw.parseAny(leaf.getObjectId());
			if (obj instanceof RevTag) {
				return new ObjectIdRef.PeeledTag(
						leaf.getStorage()
						leaf.getName()
						leaf.getObjectId()
						rw.peel(obj).copy()
						hasVersioning() ? leaf.getUpdateIndex()
								: UNDEFINED_UPDATE_INDEX);
			} else {
				return new ObjectIdRef.PeeledNonTag(
						leaf.getStorage()
						leaf.getName()
						leaf.getObjectId()
						hasVersioning() ? leaf.getUpdateIndex()
								: UNDEFINED_UPDATE_INDEX);
			}
		}
	}

	static Ref recreate(Ref old
		if (old.isSymbolic()) {
			Ref dst = recreate(old.getTarget()
			return new SymbolicRef(old.getName()
					hasVersioning ? old.getUpdateIndex()
							: UNDEFINED_UPDATE_INDEX);
		}
		return leaf;
	}

	@Override
	public RefUpdate newUpdate(String refName
			throws IOException {
		boolean detachingSymbolicRef = false;
		Ref ref = exactRef(refName);
		if (ref == null)
			ref = new ObjectIdRef.Unpeeled(NEW
		else
			detachingSymbolicRef = detach && ref.isSymbolic();

		DfsRefUpdate update = new DfsRefUpdate(this
		if (detachingSymbolicRef)
			update.setDetachingSymbolicRef();
		return update;
	}

	@Override
	public RefRename newRename(String fromName
			throws IOException {
		RefUpdate src = newUpdate(fromName
		RefUpdate dst = newUpdate(toName
		return new DfsRefRename(src
	}

	@Override
	public boolean isNameConflicting(String refName) throws IOException {
		RefList<Ref> all = read().ids;

		int lastSlash = refName.lastIndexOf('/');
		while (0 < lastSlash) {
			String needle = refName.substring(0
			if (all.contains(needle))
				return true;
			lastSlash = refName.lastIndexOf('/'
		}

		String prefix = refName + '/';
		int idx = -(all.find(prefix) + 1);
		if (idx < all.size() && all.get(idx).getName().startsWith(prefix))
			return true;
		return false;
	}

	@Override
	public void create() {
	}

	@Override
	public void refresh() {
		clearCache();
	}

	@Override
	public void close() {
		clearCache();
	}

	void clearCache() {
		cache.set(null);
	}

	void stored(Ref ref) {
		RefCache oldCache
		do {
			oldCache = cache.get();
			if (oldCache == null)
				return;
			newCache = oldCache.put(ref);
		} while (!cache.compareAndSet(oldCache
	}

	void removed(String refName) {
		RefCache oldCache
		do {
			oldCache = cache.get();
			if (oldCache == null)
				return;
			newCache = oldCache.remove(refName);
		} while (!cache.compareAndSet(oldCache
	}

	private RefCache read() throws IOException {
		RefCache c = cache.get();
		if (c == null) {
			c = scanAllRefs();
			cache.set(c);
		}
		return c;
	}

	protected abstract RefCache scanAllRefs() throws IOException;

	protected abstract boolean compareAndPut(Ref oldRef
			throws IOException;

	protected abstract boolean compareAndRemove(Ref oldRef) throws IOException;

	protected void cachePeeledState(Ref oldLeaf
		try {
			compareAndPut(oldLeaf
		} catch (IOException e) {
		}
	}

	public static class RefCache {
		final RefList<Ref> ids;

		final RefList<Ref> sym;

		public RefCache(RefList<Ref> ids
			this.ids = ids;
			this.sym = sym;
		}

		RefCache(RefList<Ref> ids
			this(ids
		}

		public int size() {
			return ids.size();
		}

		public Ref get(String name) {
			return ids.get(name);
		}

		public RefCache put(Ref ref) {
			RefList<Ref> newIds = this.ids.put(ref);
			RefList<Ref> newSym = this.sym;
			if (ref.isSymbolic()) {
				newSym = newSym.put(ref);
			} else {
				int p = newSym.find(ref.getName());
				if (0 <= p)
					newSym = newSym.remove(p);
			}
			return new RefCache(newIds
		}

		public RefCache remove(String refName) {
			RefList<Ref> newIds = this.ids;
			int p = newIds.find(refName);
			if (0 <= p)
				newIds = newIds.remove(p);

			RefList<Ref> newSym = this.sym;
			p = newSym.find(refName);
			if (0 <= p)
				newSym = newSym.remove(p);
			return new RefCache(newIds
		}
	}
}
