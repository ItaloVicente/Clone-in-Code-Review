package org.eclipse.jgit.internal.storage.memory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefCache;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;

public class InMemoryRefDatabase extends RefDatabase
		implements RefCache {

	private final RefDatabase refDb;

	private final TernarySearchTree<Ref> cache;

	public InMemoryRefDatabase(Repository repo) {
		this.refDb = repo.getRefDatabase();
		this.cache = new TernarySearchTree<>();
	}

	public int reload() {
		try {
			return cache.reload(refDb.getRefs(ALL).entrySet());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void create() throws IOException {
		reload();
	}

	@Override
	public void close() {
		refDb.close();
		cache.clear();
	}

	@Override
	public boolean isNameConflicting(String name) throws IOException {
		return refDb.isNameConflicting(name);
	}

	@Override
	public RefUpdate newUpdate(String name
			throws IOException {
		RefUpdate update = refDb.newUpdate(name
		update.setRefCache(this);
		return update;
	}

	@Override
	public BatchRefUpdate newBatchUpdate() {
		BatchRefUpdate batchUpdate = refDb.newBatchUpdate();
		batchUpdate.setRefCache(this);
		return batchUpdate;
	}

	@Override
	public RefRename newRename(String fromName
			throws IOException {
		return refDb.newRename(fromName
	}

	@Override
	public Ref exactRef(String name) throws IOException {
		Ref ref = cache.get(name);
		return ref;
	}

	@Override
	public Map<String
		Map<String
		for (String key : cache.getKeysWithPrefix(prefix)) {
			refs.put(key
		}
		return refs;
	}

	@Override
	public List<Ref> getAdditionalRefs() throws IOException {
		List<Ref> addtlRefs = new LinkedList<>();
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

	@Override
	public void onUpdated(RefUpdate updated
		cache.insert(updated.getName()

	}

	@Override
	public void onDeleted(RefUpdate deleted
		cache.delete(deleted.getName());
	}

	@Override
	public void onLinked(RefUpdate linked
		cache.insert(linked.getName()
	}

	@Override
	public void onRenamed(RefUpdate src
		cache.delete(src.getName());
		cache.insert(dst.getName()
	}

	@Override
	public void onBatchUpdated(Iterable<Entry<String
		cache.reload(newRefs);
	}
}
