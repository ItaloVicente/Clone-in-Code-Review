
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Ref.Storage.LOOSE;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef.PeeledNonTag;
import org.eclipse.jgit.lib.ObjectIdRef.PeeledTag;
import org.eclipse.jgit.lib.ObjectIdRef.Unpeeled;
import org.eclipse.jgit.lib.ObjectIdSubclassMap;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.dht.spi.RefTransaction;
import org.eclipse.jgit.util.RefList;
import org.eclipse.jgit.util.RefMap;

class DhtRefDatabase extends RefDatabase {
	private final DhtRepository repository;

	private final RepositoryKey repo;

	private final Database db;

	private final AtomicReference<RefCache> cache;

	DhtRefDatabase(DhtRepository repository
		this.repository = repository;
		this.repo = repo;
		this.db = db;
		this.cache = new AtomicReference<RefCache>();
	}

	DhtRepository getRepository() {
		return repository;
	}

	ChunkKey find(AnyObjectId id) {
		RefCache c = cache.get();
		if (c != null) {
			RefData.IdWithChunk i = c.hints.get(id);
			if (i != null)
				return i.getChunkKey();
		}
		return null;
	}

	@Override
	public Ref getRef(String needle) throws IOException {
		Map<String
		for (String prefix : SEARCH_PATH) {
			Ref ref = all.get(prefix + needle);
			if (ref != null)
				return ref;
		}
		return null;
	}

	@Override
	public List<Ref> getAdditionalRefs() {
		return Collections.emptyList();
	}

	@Override
	public Map<String
		final RefCache c = readRefs();

		RefList<Ref> packed = RefList.emptyList();
		RefList<Ref> loose = c.ids;
		RefList.Builder<Ref> sym = new RefList.Builder<Ref>(c.sym.size());

		for (int idx = 0; idx < c.sym.size(); idx++) {
			Ref ref = c.sym.get(idx);
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

		RefCache cur = readRefs();
		int idx = cur.ids.find(oldLeaf.getName());
		if (0 <= idx && cur.ids.get(idx) == oldLeaf) {
			RefList<Ref> newList = cur.ids.set(idx
			if (cache.compareAndSet(cur
				cachePeeledState(oldLeaf
		}

		return recreate(ref
	}

	private void cachePeeledState(Ref oldLeaf
		try {
			RefKey key = RefKey.create(repo
			RefTransaction txn = db.ref().newTransaction(key);
			if (RefData.fromRef(oldLeaf).equals(txn.getOldData()))
				txn.compareAndPut(RefData.fromRef(newLeaf));
			else
				txn.abort();
		} catch (TimeoutException e) {

		} catch (DhtException e) {
		}
	}

	private Ref doPeel(final Ref leaf) throws MissingObjectException
			IOException {
		RevWalk rw = new RevWalk(getRepository());
		try {
			String name = leaf.getName();
			ObjectId oId = leaf.getObjectId();
			RevObject obj = rw.parseAny(oId);
			DhtReader ctx = (DhtReader) rw.getObjectReader();

			ChunkKey key = ctx.findChunk(oId);
			if (key != null)
				oId = new RefData.IdWithChunk(oId

			if (obj instanceof RevTag) {
				ObjectId pId = rw.peel(obj);
				key = ctx.findChunk(pId);
				pId = key != null ? new RefData.IdWithChunk(pId
						.copy();
				return new PeeledTag(leaf.getStorage()
			} else {
				return new PeeledNonTag(leaf.getStorage()
			}
		} finally {
			rw.release();
		}
	}

	private static Ref recreate(final Ref old
		if (old.isSymbolic()) {
			Ref dst = recreate(old.getTarget()
			return new SymbolicRef(old.getName()
		}
		return leaf;
	}

	@Override
	public DhtRefUpdate newUpdate(String refName
			throws IOException {
		Ref ref = getRefs(ALL).get(refName);
		if (ref == null)
			ref = new Unpeeled(NEW
		return new DhtRefUpdate(this
	}

	@Override
	public RefRename newRename(String fromName
			throws IOException {
		DhtRefUpdate src = newUpdate(fromName
		DhtRefUpdate dst = newUpdate(toName
		return new DhtRefRename(src
	}

	@Override
	public boolean isNameConflicting(String refName) throws IOException {
		RefList<Ref> all = readRefs().ids;

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
	public void close() {
		clearCache();
	}

	void clearCache() {
		cache.set(null);
	}

	private RefCache readRefs() throws DhtException {
		RefCache c = cache.get();
		if (c == null) {
			try {
				c = read();
			} catch (TimeoutException e) {
				throw new DhtTimeoutException(e);
			}
			cache.set(c);
		}
		return c;
	}

	private RefCache read() throws DhtException
		RefList.Builder<Ref> id = new RefList.Builder<Ref>();
		RefList.Builder<Ref> sym = new RefList.Builder<Ref>();
		ObjectIdSubclassMap<RefData.IdWithChunk> hints = new ObjectIdSubclassMap<RefData.IdWithChunk>();

		SCAN: for (Map.Entry<RefKey
			String name = e.getKey().getName();
			RefData data = e.getValue();

			ObjectId oId = null;
			boolean peeled = false;
			ObjectId pId = null;

			TinyProtobuf.Decoder d = data.decode();
			DECODE: for (;;) {
				switch (d.next()) {
				case 0:
					break DECODE;

				case RefData.TAG_SYMREF: {
					String symref = d.string();
					Ref leaf = new Unpeeled(NEW
					SymbolicRef ref = new SymbolicRef(name
					id.add(ref);
					sym.add(ref);
					continue SCAN;
				}

				case RefData.TAG_TARGET:
					oId = RefData.IdWithChunk.decode(d.message());
					continue;
				case RefData.TAG_IS_PEELED:
					peeled = d.bool();
					continue;
				case RefData.TAG_PEELED:
					pId = RefData.IdWithChunk.decode(d.message());
					continue;
				default:
					d.skip();
					continue;
				}
			}

			if (oId instanceof RefData.IdWithChunk && !hints.contains(oId))
				hints.add((RefData.IdWithChunk) oId);
			if (pId instanceof RefData.IdWithChunk && !hints.contains(pId))
				hints.add((RefData.IdWithChunk) pId);

			if (peeled && pId != null)
				id.add(new PeeledTag(LOOSE
			else if (peeled)
				id.add(new PeeledNonTag(LOOSE
			else
				id.add(new Unpeeled(LOOSE
		}

		id.sort();
		sym.sort();

		return new RefCache(id.toRefList()
	}

	private Set<Map.Entry<RefKey
			TimeoutException {
		return db.ref().getAll(Context.LOCAL
	}

	private static class RefCache {
		final RefList<Ref> ids;

		final RefList<Ref> sym;

		final ObjectIdSubclassMap<RefData.IdWithChunk> hints;

		RefCache(RefList<Ref> ids
				ObjectIdSubclassMap<RefData.IdWithChunk> hints) {
			this.ids = ids;
			this.sym = sym;
			this.hints = hints;
		}

		RefCache(RefList<Ref> ids
			this(ids
		}
	}
}
