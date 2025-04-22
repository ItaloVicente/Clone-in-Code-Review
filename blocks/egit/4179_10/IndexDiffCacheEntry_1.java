package org.eclipse.egit.core.internal.indexdiff;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.IndexDiff;
import org.eclipse.jgit.lib.Repository;

public class IndexDiffCache {

	private Map<Repository, IndexDiffCacheEntry> entries = new HashMap<Repository, IndexDiffCacheEntry>();

	private Set<IndexDiffChangedListener> listeners = new HashSet<IndexDiffChangedListener>();

	private IndexDiffChangedListener globalListener;

	public IndexDiffCache() {
		createGlobalListener();
	}

	public IndexDiffCacheEntry getIndexDiffCacheEntry(Repository repository) {
		IndexDiffCacheEntry entry;
		synchronized (entries) {
			entry = entries.get(repository);
			if (entry != null)
				return entry;
			entry = new IndexDiffCacheEntry(repository);
			entries.put(repository, entry);
		}
		entry.addIndexDiffChangedListener(globalListener);
		return entry;
	}

	public void addIndexDiffChangedListener(IndexDiffChangedListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void removeIndexDiffChangedListener(IndexDiffChangedListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	private void createGlobalListener() {
		globalListener = new IndexDiffChangedListener() {
			public void indexDiffChanged(Repository repository,
					IndexDiffData indexDiffData) {
				notifyListeners(repository, indexDiffData);
			}
		};
	}

	private void notifyListeners(Repository repository,
			IndexDiffData indexDiffData) {
		IndexDiffChangedListener[] tmpListeners;
		synchronized (listeners) {
			tmpListeners = listeners
					.toArray(new IndexDiffChangedListener[listeners.size()]);
		}
		for (int i = 0; i < tmpListeners.length; i++)
			tmpListeners[i].indexDiffChanged(repository, indexDiffData);
	}

}
