package org.eclipse.jgit.internal.storage.memory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import org.eclipse.jgit.internal.storage.file.RefDirectory;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefCache;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.SymbolicRef;

public class InMemoryRefDatabase extends RefDatabase implements RefCache {

	private final RefDatabase refDb;

	private final TernarySearchTree<Ref> cache;

	public InMemoryRefDatabase(RefDatabase refDir) {
		this.refDb = refDir;
		this.cache = new TernarySearchTree<>();
		reload();
	}

	@Override
	public void create() throws IOException {
		refDb.create();
		reload();
	}

	@Override
	public void close() {
		refDb.close();
	}

	@Override
	public boolean isNameConflicting(String name) throws IOException {
		return refDb.isNameConflicting(name);
	}

	@Override
	public RefUpdate newUpdate(String name
		RefUpdate update = refDb.newUpdate(name
		return update;
	}

	@Override
	public BatchRefUpdate newBatchUpdate() {
		BatchRefUpdate batchUpdate = refDb.newBatchUpdate();
		return batchUpdate;
	}

	@Override
	public RefRename newRename(String fromName
			throws IOException {
		RefRename rename = refDb.newRename(fromName
		return rename;
	}

	@Override
	public Ref exactRef(String name) throws IOException {
		Ref ref = readAndResolve(name);
		return ref;
	}

	public RefDatabase getWrappedRefDatabase() {
		return refDb;
	}

	private Ref readAndResolve(String name) throws IOException {
		Ref ref = cache.get(name);
		if (ref != null) {
			ref = resolve(ref
		}
		return ref;
	}

	private Ref resolve(final Ref ref
			throws IOException {
		if (ref.isSymbolic()) {
			Ref dst = ref.getTarget();
			if (MAX_SYMBOLIC_REF_DEPTH <= depth) {
			}

			dst = cache.get(dst.getName());
			if (dst == null) {
				return ref;
			}

			dst = resolve(dst
			if (dst == null) {
				return null;
			}
			return new SymbolicRef(ref.getName()
		}
		return ref;
	}

	@Override
	public Map<String
		Map<String
		for (Entry<String
			Ref ref = resolve(e.getValue()
			if (ref.isSymbolic()) {
				Ref dst = ref.getTarget();
				if (refDb.exactRef(dst.getName()) == null) {
					continue;
				}
			}
			refs.put(toMapKey(prefix
		}
		return Collections.unmodifiableMap(refs);
	}

	@Override
	public List<Ref> getRefsByPrefix(String prefix) throws IOException {
		List<Ref> refs = new ArrayList<>();
		List<Ref> matching = cache.getValuesWithPrefix(prefix);
		for (Ref r : matching) {
			Ref ref = resolve(r
			refs.add(ref);
		}
		return Collections.unmodifiableList(refs);
	}

	private String toMapKey(String prefix
		String name = ref.getName();
		if (0 < prefix.length())
			name = name.substring(prefix.length());
		return name;
	}

	@Override
	public List<Ref> getAdditionalRefs() throws IOException {
		List<Ref> addtlRefs = new ArrayList<>();
		for (String name : additionalRefsNames) {
			Ref r = exactRef(name);
			if (r != null) {
				addtlRefs.add(r);
			}
		}
		return addtlRefs;
	}

	@Override
	public Ref peel(Ref ref) throws IOException {
		return ref.getLeaf();
	}

	public void log(boolean force
			throws IOException {
		if (refDb instanceof RefDirectory) {
			((RefDirectory) refDb).log(force
		}
	}

	@Override
	public ReadWriteLock getLock() {
		return cache.getLock();
	}

	@Override
	public int reload() {
		try {
			Map<String
			List<Ref> symRefs = refDb.getAdditionalRefs();
			Ref h = refDb.exactRef(Constants.HEAD);
			if (h != null) {
				symRefs.add(h);
			}
			for (Ref s : symRefs) {
				refs.put(s.getName()
			}
			return cache.replace(refs.entrySet());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public int update(Iterable<String> reload
		Lock lck = cache.getLock().writeLock();
		lck.lock();
		try {
			for (String refName : reload) {
				Ref r = refDb.exactRef(refName);
				if (r != null) {
					cache.insert(refName
				} else {
					cache.delete(refName);
				}
			}
			for (String refName : delete) {
				cache.delete(refName);
			}
			return cache.size();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} finally {
			lck.unlock();
		}
	}

	@Override
	public int replace(Iterable<Entry<String
		return cache.replace(newCacheContent);
	}

	@Override
	public void insert(Ref ref) {
		cache.insert(ref.getName()
		if (ref.isSymbolic()) {
			Ref leaf = ref.getLeaf();
			cache.insert(leaf.getName()
		}
	}

	@Override
	public void delete(String refName) {
		cache.delete(refName);
	}

	@Override
	public void rename(Ref oldRef
		cache.delete(oldRef.getName());
		cache.insert(newRef.getName()
	}
}
