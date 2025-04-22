
package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.stream.LongStream;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.PackExt;

public final class DfsBlockCache {
	private static volatile DfsBlockCache cache;

	static {
		reconfigure(new DfsBlockCacheConfig());
	}

	public static void reconfigure(DfsBlockCacheConfig cfg) {
		cache = new DfsBlockCache(cfg);
	}

	public static DfsBlockCache getInstance() {
		return cache;
	}

	private final int tableSize;

	private final AtomicReferenceArray<HashEntry> table;

	private final ReentrantLock[] loadLocks;

	private final ReentrantLock[] refLocks;

	private final long maxBytes;

	private final long maxStreamThroughCache;

	private final int blockSize;

	private final int blockSizeShift;

	private final AtomicReference<AtomicLong[]> statHit;

	private final AtomicReference<AtomicLong[]> statMiss;

	private final AtomicReference<AtomicLong[]> statEvict;

	private final AtomicReference<AtomicLong[]> liveBytes;

	private final ReentrantLock clockLock;

	private final Consumer<Long> refLockWaitTime;

	private Ref clockHand;

	@SuppressWarnings("unchecked")
	private DfsBlockCache(DfsBlockCacheConfig cfg) {
		tableSize = tableSize(cfg);
		if (tableSize < 1) {
			throw new IllegalArgumentException(JGitText.get().tSizeMustBeGreaterOrEqual1);
		}

		table = new AtomicReferenceArray<>(tableSize);
		loadLocks = new ReentrantLock[cfg.getConcurrencyLevel()];
		for (int i = 0; i < loadLocks.length; i++) {
		}
		refLocks = new ReentrantLock[cfg.getConcurrencyLevel()];
		for (int i = 0; i < refLocks.length; i++) {
		}

		maxBytes = cfg.getBlockLimit();
		maxStreamThroughCache = (long) (maxBytes * cfg.getStreamRatio());
		blockSize = cfg.getBlockSize();
		blockSizeShift = Integer.numberOfTrailingZeros(blockSize);

		clockHand = new Ref<>(
				DfsStreamKey.of(new DfsRepositoryDescription(none)
				-1
		clockHand.next = clockHand;

		statHit = new AtomicReference<>(newCounters());
		statMiss = new AtomicReference<>(newCounters());
		statEvict = new AtomicReference<>(newCounters());
		liveBytes = new AtomicReference<>(newCounters());

		refLockWaitTime = cfg.getRefLockWaitTimeConsumer();
	}

	boolean shouldCopyThroughCache(long length) {
		return length <= maxStreamThroughCache;
	}

	public long[] getCurrentSize() {
		return getStatVals(liveBytes);
	}

	public long getFillPercentage() {
		return LongStream.of(getCurrentSize()).sum() * 100 / maxBytes;
	}

	public long[] getHitCount() {
		return getStatVals(statHit);
	}

	public long[] getMissCount() {
		return getStatVals(statMiss);
	}

	public long[] getTotalRequestCount() {
		AtomicLong[] hit = statHit.get();
		AtomicLong[] miss = statMiss.get();
		long[] cnt = new long[Math.max(hit.length
		for (int i = 0; i < hit.length; i++) {
			cnt[i] += hit[i].get();
		}
		for (int i = 0; i < miss.length; i++) {
			cnt[i] += miss[i].get();
		}
		return cnt;
	}

	public long[] getHitRatio() {
		AtomicLong[] hit = statHit.get();
		AtomicLong[] miss = statMiss.get();
		long[] ratio = new long[Math.max(hit.length
		for (int i = 0; i < ratio.length; i++) {
			if (i >= hit.length) {
				ratio[i] = 0;
			} else if (i >= miss.length) {
				ratio[i] = 100;
			} else {
				long hitVal = hit[i].get();
				long missVal = miss[i].get();
				long total = hitVal + missVal;
				ratio[i] = total == 0 ? 0 : hitVal * 100 / total;
			}
		}
		return ratio;
	}

	public long[] getEvictions() {
		return getStatVals(statEvict);
	}

	public boolean hasBlock0(DfsStreamKey key) {
		HashEntry e1 = table.get(slot(key
		DfsBlock v = scan(e1
		return v != null && v.contains(key
	}

	private int hash(int packHash
		return packHash + (int) (off >>> blockSizeShift);
	}

	int getBlockSize() {
		return blockSize;
	}

	private static int tableSize(DfsBlockCacheConfig cfg) {
		final int wsz = cfg.getBlockSize();
		final long limit = cfg.getBlockLimit();
		if (wsz <= 0) {
			throw new IllegalArgumentException(JGitText.get().invalidWindowSize);
		}
		if (limit < wsz) {
			throw new IllegalArgumentException(JGitText.get().windowSizeMustBeLesserThanLimit);
		}
		return (int) Math.min(5 * (limit / wsz) / 2
	}

	DfsBlock getOrLoad(BlockBasedFile file
			ReadableChannelSupplier fileChannel) throws IOException {
		final long requestedPosition = position;
		position = file.alignToBlock(position);

		DfsStreamKey key = file.key;
		int slot = slot(key
		HashEntry e1 = table.get(slot);
		DfsBlock v = scan(e1
		if (v != null && v.contains(key
			ctx.stats.blockCacheHit++;
			getStat(statHit
			return v;
		}

		reserveSpace(blockSize
		ReentrantLock regionLock = lockFor(key
		regionLock.lock();
		try {
			HashEntry e2 = table.get(slot);
			if (e2 != e1) {
				v = scan(e2
				if (v != null) {
					ctx.stats.blockCacheHit++;
					getStat(statHit
					creditSpace(blockSize
					return v;
				}
			}

			getStat(statMiss
			boolean credit = true;
			try {
				v = file.readOneBlock(requestedPosition
						fileChannel.get());
				credit = false;
			} finally {
				if (credit) {
					creditSpace(blockSize
				}
			}
			if (position != v.start) {
				position = v.start;
				slot = slot(key
				e2 = table.get(slot);
			}

			Ref<DfsBlock> ref = new Ref<>(key
			ref.hot = true;
			for (;;) {
				HashEntry n = new HashEntry(clean(e2)
				if (table.compareAndSet(slot
					break;
				}
				e2 = table.get(slot);
			}
			addToClock(ref
		} finally {
			regionLock.unlock();
		}

		if (v.contains(file.key
			return v;
		}
		return getOrLoad(file
	}

	@SuppressWarnings("unchecked")
	private void reserveSpace(int reserve
		clockLock.lock();
		try {
			long live = LongStream.of(getCurrentSize()).sum() + reserve;
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
					getStat(liveBytes
					getStat(statEvict
				} while (maxBytes < live);
				clockHand = prev;
			}
			getStat(liveBytes
		} finally {
			clockLock.unlock();
		}
	}

	private void creditSpace(int credit
		clockLock.lock();
		try {
			getStat(liveBytes
		} finally {
			clockLock.unlock();
		}
	}

	@SuppressWarnings("unchecked")
	private void addToClock(Ref ref
		clockLock.lock();
		try {
			if (credit != 0) {
				getStat(liveBytes
			}
			Ref ptr = clockHand;
			ref.next = ptr.next;
			ptr.next = ref;
			clockHand = ref;
		} finally {
			clockLock.unlock();
		}
	}

	void put(DfsBlock v) {
		put(v.stream
	}

	<T> Ref<T> getOrLoadRef(DfsStreamKey key
			throws IOException {
		int slot = slot(key
		HashEntry e1 = table.get(slot);
		Ref<T> ref = scanRef(e1
		if (ref != null) {
			getStat(statHit
			return ref;
		}

		ReentrantLock regionLock = lockForRef(key);
		long lockStart = System.currentTimeMillis();
		regionLock.lock();
		try {
			HashEntry e2 = table.get(slot);
			if (e2 != e1) {
				ref = scanRef(e2
				if (ref != null) {
					getStat(statHit
					return ref;
				}
			}

			if (refLockWaitTime != null) {
				refLockWaitTime.accept(
						Long.valueOf(System.currentTimeMillis() - lockStart));
			}
			getStat(statMiss
			ref = loader.load();
			ref.hot = true;
			reserveSpace(ref.size
			for (;;) {
				HashEntry n = new HashEntry(clean(e2)
				if (table.compareAndSet(slot
					break;
				}
				e2 = table.get(slot);
			}
			addToClock(ref
		} finally {
			regionLock.unlock();
		}
		return ref;
	}

	<T> Ref<T> putRef(DfsStreamKey key
		return put(key
	}

	<T> Ref<T> put(DfsStreamKey key
		int slot = slot(key
		HashEntry e1 = table.get(slot);
		Ref<T> ref = scanRef(e1
		if (ref != null) {
			return ref;
		}

		reserveSpace(size
		ReentrantLock regionLock = lockFor(key
		regionLock.lock();
		try {
			HashEntry e2 = table.get(slot);
			if (e2 != e1) {
				ref = scanRef(e2
				if (ref != null) {
					creditSpace(size
					return ref;
				}
			}

			ref = new Ref<>(key
			ref.hot = true;
			for (;;) {
				HashEntry n = new HashEntry(clean(e2)
				if (table.compareAndSet(slot
					break;
				}
				e2 = table.get(slot);
			}
			addToClock(ref
		} finally {
			regionLock.unlock();
		}
		return ref;
	}

	boolean contains(DfsStreamKey key
		return scan(table.get(slot(key
	}

	@SuppressWarnings("unchecked")
	<T> T get(DfsStreamKey key
		T val = (T) scan(table.get(slot(key
		if (val == null) {
			getStat(statMiss
		} else {
			getStat(statHit
		}
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
			if (r.position == position && r.key.equals(key)) {
				return r.get() != null ? r : null;
			}
		}
		return null;
	}

	private int slot(DfsStreamKey key
		return (hash(key.hash
	}

	private ReentrantLock lockFor(DfsStreamKey key
		return loadLocks[(hash(key.hash
	}

	private ReentrantLock lockForRef(DfsStreamKey key) {
		return refLocks[(key.hash >>> 1) % refLocks.length];
	}

	private static AtomicLong[] newCounters() {
		AtomicLong[] ret = new AtomicLong[PackExt.values().length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new AtomicLong();
		}
		return ret;
	}

	private static AtomicLong getStat(AtomicReference<AtomicLong[]> stats
			DfsStreamKey key) {
		int pos = key.packExtPos;
		while (true) {
			AtomicLong[] vals = stats.get();
			if (pos < vals.length) {
				return vals[pos];
			}
			AtomicLong[] expect = vals;
			vals = new AtomicLong[Math.max(pos + 1
			System.arraycopy(expect
			for (int i = expect.length; i < vals.length; i++) {
				vals[i] = new AtomicLong();
			}
			if (stats.compareAndSet(expect
				return vals[pos];
			}
		}
	}

	private static long[] getStatVals(AtomicReference<AtomicLong[]> stat) {
		AtomicLong[] stats = stat.get();
		long[] cnt = new long[stats.length];
		for (int i = 0; i < stats.length; i++) {
			cnt[i] = stats[i].get();
		}
		return cnt;
	}

	private static HashEntry clean(HashEntry top) {
		while (top != null && top.ref.next == null)
			top = top.next;
		if (top == null) {
			return null;
		}
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

	static final class Ref<T> {
		final DfsStreamKey key;
		final long position;
		final int size;
		volatile T value;
		Ref next;
		volatile boolean hot;

		Ref(DfsStreamKey key
			this.key = key;
			this.position = position;
			this.size = size;
			this.value = v;
		}

		T get() {
			T v = value;
			if (v != null) {
				hot = true;
			}
			return v;
		}

		boolean has() {
			return value != null;
		}
	}

	@FunctionalInterface
	interface RefLoader<T> {
		Ref<T> load() throws IOException;
	}

	@FunctionalInterface
	interface ReadableChannelSupplier {
		ReadableChannel get() throws IOException;
	}
}
