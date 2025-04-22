
package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.internal.JGitText;

public final class DefaultDfsBlockCache extends DfsBlockCache {

	public static void reconfigure(final DefaultDfsBlockCacheConfig cfg) {
		DfsBlockCache.setInstance(new DefaultDfsBlockCache(cfg));
	}

	private final int tableSize;

	private final AtomicReferenceArray<HashEntry> table;

	private final ReentrantLock[] loadLocks;

	private final long maxBytes;

	private final long maxStreamThroughCache;

	private final int blockSize;

	private final int blockSizeShift;

	private final Map<DfsPackDescription

	private final Collection<DfsPackFile> packFiles;

	private final AtomicLong statHit;

	private final AtomicLong statMiss;

	private volatile long statEvict;

	private final ReentrantLock clockLock;

	private Ref clockHand;

	private volatile long liveBytes;

	@SuppressWarnings("unchecked")
	private DefaultDfsBlockCache(final DefaultDfsBlockCacheConfig cfg) {
		tableSize = tableSize(cfg);
		if (tableSize < 1)
			throw new IllegalArgumentException(JGitText.get().tSizeMustBeGreaterOrEqual1);

		table = new AtomicReferenceArray<>(tableSize);
		loadLocks = new ReentrantLock[cfg.getConcurrencyLevel()];
		for (int i = 0; i < loadLocks.length; i++)

		maxBytes = cfg.getBlockLimit();
		maxStreamThroughCache = (long) (maxBytes * cfg.getStreamRatio());
		blockSize = cfg.getBlockSize();
		blockSizeShift = Integer.numberOfTrailingZeros(blockSize);

		clockHand = new Ref<>(new DfsPackKey()
		clockHand.next = clockHand;

		packCache = new ConcurrentHashMap<>(
				16
		packFiles = Collections.unmodifiableCollection(packCache.values());

		statHit = new AtomicLong();
		statMiss = new AtomicLong();
	}

	boolean shouldCopyThroughCache(long length) {
		return length <= maxStreamThroughCache;
	}

	public long getCurrentSize() {
		return liveBytes;
	}

	public long getFillPercentage() {
		return getCurrentSize() * 100 / maxBytes;
	}

	public long getHitCount() {
		return statHit.get();
	}

	public long getMissCount() {
		return statMiss.get();
	}

	public long getTotalRequestCount() {
		return getHitCount() + getMissCount();
	}

	public long getHitRatio() {
		long hits = statHit.get();
		long miss = statMiss.get();
		long total = hits + miss;
		if (total == 0)
			return 0;
		return hits * 100 / total;
	}

	public long getEvictions() {
		return statEvict;
	}

	public Collection<DfsPackFile> getPackFiles() {
		return packFiles;
	}

	DfsPackFile getOrCreate(DfsPackDescription dsc

		DfsPackFile pack = packCache.get(dsc);
		if (pack != null && !pack.invalid()) {
			return pack;
		}

		return packCache.compute(dsc
			} else {
				return new DfsPackFile(
						this
			}
		});
	}

	private int hash(int packHash
		return packHash + (int) (off >>> blockSizeShift);
	}

	int getBlockSize() {
		return blockSize;
	}

	private static int tableSize(final DefaultDfsBlockCacheConfig cfg) {
		final int wsz = cfg.getBlockSize();
		final long limit = cfg.getBlockLimit();
		if (wsz <= 0)
			throw new IllegalArgumentException(JGitText.get().invalidWindowSize);
		if (limit < wsz)
			throw new IllegalArgumentException(JGitText.get().windowSizeMustBeLesserThanLimit);
		return (int) Math.min(5 * (limit / wsz) / 2
	}

	DfsBlock getOrLoad(DfsPackFile pack
			throws IOException {
		final long requestedPosition = position;
		position = pack.alignToBlock(position);

		DfsPackKey key = pack.key;
		int slot = slot(key
		HashEntry e1 = table.get(slot);
		DfsBlock v = scan(e1
		if (v != null) {
			statHit.incrementAndGet();
			return v;
		}

		reserveSpace(blockSize);
		ReentrantLock regionLock = lockFor(key
		regionLock.lock();
		try {
			HashEntry e2 = table.get(slot);
			if (e2 != e1) {
				v = scan(e2
				if (v != null) {
					statHit.incrementAndGet();
					creditSpace(blockSize);
					return v;
				}
			}

			statMiss.incrementAndGet();
			boolean credit = true;
			try {
				v = pack.readOneBlock(position
				credit = false;
			} finally {
				if (credit)
					creditSpace(blockSize);
			}
			if (position != v.start) {
				position = v.start;
				slot = slot(key
				e2 = table.get(slot);
			}

			key.cachedSize.addAndGet(v.size());
			Ref<DfsBlock> ref = new Ref<>(key
			ref.hot = true;
			for (;;) {
				HashEntry n = new HashEntry(clean(e2)
				if (table.compareAndSet(slot
					break;
				e2 = table.get(slot);
			}
			addToClock(ref
		} finally {
			regionLock.unlock();
		}

		if (v.contains(pack.key
			return v;
		return getOrLoad(pack
	}

	@SuppressWarnings("unchecked")
	private void reserveSpace(int reserve) {
		clockLock.lock();
		try {
			long live = liveBytes + reserve;
			if (maxBytes < live) {
				Ref prev = clockHand;
				Ref hand = clockHand.next;
				do {
					if (hand.hot) {
						hand.hot = false;
						prev = hand;
						hand = hand.next;
						continue;
					} else if (prev == hand)
						break;

					Ref dead = hand;
					hand = hand.next;
					prev.next = hand;
					dead.next = null;
					dead.value = null;
					live -= dead.size;
					dead.pack.cachedSize.addAndGet(-dead.size);
					statEvict++;
				} while (maxBytes < live);
				clockHand = prev;
			}
			liveBytes = live;
		} finally {
			clockLock.unlock();
		}
	}

	private void creditSpace(int credit) {
		clockLock.lock();
		liveBytes -= credit;
		clockLock.unlock();
	}

	@SuppressWarnings("unchecked")
	private void addToClock(Ref ref
		clockLock.lock();
		try {
			if (credit != 0)
				liveBytes -= credit;
			Ref ptr = clockHand;
			ref.next = ptr.next;
			ptr.next = ref;
			clockHand = ref;
		} finally {
			clockLock.unlock();
		}
	}

	void put(DfsBlock v) {
		put(v.pack
	}

	<T> Ref<T> put(DfsPackKey key
		int slot = slot(key
		HashEntry e1 = table.get(slot);
		Ref<T> ref = scanRef(e1
		if (ref != null)
			return ref;

		reserveSpace(size);
		ReentrantLock regionLock = lockFor(key
		regionLock.lock();
		try {
			HashEntry e2 = table.get(slot);
			if (e2 != e1) {
				ref = scanRef(e2
				if (ref != null) {
					creditSpace(size);
					return ref;
				}
			}

			key.cachedSize.addAndGet(size);
			ref = new Ref<>(key
			ref.hot = true;
			for (;;) {
				HashEntry n = new HashEntry(clean(e2)
				if (table.compareAndSet(slot
					break;
				e2 = table.get(slot);
			}
			addToClock(ref
		} finally {
			regionLock.unlock();
		}
		return ref;
	}

	boolean contains(DfsPackKey key
		return scan(table.get(slot(key
	}

	@SuppressWarnings("unchecked")
	<T> T get(DfsPackKey key
		T val = (T) scan(table.get(slot(key
		if (val == null)
			statMiss.incrementAndGet();
		else
			statHit.incrementAndGet();
		return val;
	}

	private <T> T scan(HashEntry n
		Ref<T> r = scanRef(n
		return r != null ? r.get() : null;
	}

	@SuppressWarnings("unchecked")
	private <T> Ref<T> scanRef(HashEntry n
		for (; n != null; n = n.next) {
			Ref<T> r = n.ref;
			if (r.pack == pack && r.position == position)
				return r.get() != null ? r : null;
		}
		return null;
	}

	void remove(DfsPackFile pack) {
		packCache.remove(pack.getPackDescription());
	}

	void cleanUp() {
		for (DfsPackFile pack : getPackFiles())
			pack.key.cachedSize.set(0);
	}

	private int slot(DfsPackKey pack
		return (hash(pack.hash
	}

	private ReentrantLock lockFor(DfsPackKey pack
		return loadLocks[(hash(pack.hash
	}

	private static HashEntry clean(HashEntry top) {
		while (top != null && top.ref.next == null)
			top = top.next;
		if (top == null)
			return null;
		HashEntry n = clean(top.next);
		return n == top.next ? top : new HashEntry(n
	}

	private static final class HashEntry {
		final HashEntry next;

		final Ref ref;

		HashEntry(HashEntry n
			next = n;
			ref = r;
		}
	}

	static final class Ref<T> extends DfsBlockCache.Ref<T> {
		final DfsPackKey pack;
		final long position;
		final int size;
		volatile T value;
		Ref next;
		volatile boolean hot;

		Ref(DfsPackKey pack
			this.pack = pack;
			this.position = position;
			this.size = size;
			this.value = v;
		}

		T get() {
			T v = value;
			if (v != null)
				hot = true;
			return v;
		}

		boolean has() {
			return value != null;
		}
	}
}
