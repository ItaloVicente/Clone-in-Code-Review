package org.eclipse.jgit.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.internal.JGitText;

public class SimpleLruCache<K

	private static class Entry<K

		private final K key;

		private final V value;

		private volatile long lastAccessed;

		private long lastAccessedSorting;

		Entry(K key
			this.key = key;
			this.value = value;
			this.lastAccessed = lastAccessed;
		}

		void copyAccessTime() {
			lastAccessedSorting = lastAccessed;
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			return "Entry [lastAccessed=" + lastAccessed + "
					+ "
		}
	}

	private Lock lock = new ReentrantLock();

	private Map<K

	private volatile int maximumSize;

	private int purgeSize;

	private volatile long time = 0L;

	private static void checkPurgeFactor(float purgeFactor) {
		if (purgeFactor <= 0 || purgeFactor >= 1) {
			throw new IllegalArgumentException(
					MessageFormat.format(JGitText.get().invalidPurgeFactor
							Float.valueOf(purgeFactor)));
		}
	}

	private static int purgeSize(int maxSize
		return (int) ((1 - purgeFactor) * maxSize);
	}

	public SimpleLruCache(int maxSize
		checkPurgeFactor(purgeFactor);
		this.maximumSize = maxSize;
		this.purgeSize = purgeSize(maxSize
	}

	@SuppressWarnings("NonAtomicVolatileUpdate")
	public V get(Object key) {
		Entry<K
		if (entry != null) {
			entry.lastAccessed = ++time;
			return entry.value;
		}
		return null;
	}

	@SuppressWarnings("NonAtomicVolatileUpdate")
	public V put(@NonNull K key
		map.put(key
		if (map.size() > maximumSize) {
			purge();
		}
		return value;
	}

	public int size() {
		return map.size();
	}

	public void configure(int maxSize
		lock.lock();
		try {
			checkPurgeFactor(purgeFactor);
			this.maximumSize = maxSize;
			this.purgeSize = purgeSize(maxSize
			if (map.size() >= maximumSize) {
				purge();
			}
		} finally {
			lock.unlock();
		}
	}

	private void purge() {
		if (lock.tryLock()) {
			try {
				List<Entry> entriesToPurge = new ArrayList<>(map.values());
				for (Entry e : entriesToPurge) {
					e.copyAccessTime();
				}
				Collections.sort(entriesToPurge
						Comparator.comparingLong(o -> -o.lastAccessedSorting));
				for (int index = purgeSize; index < entriesToPurge
						.size(); index++) {
					map.remove(entriesToPurge.get(index).key);
				}
			} finally {
				lock.unlock();
			}
		}
	}
}
