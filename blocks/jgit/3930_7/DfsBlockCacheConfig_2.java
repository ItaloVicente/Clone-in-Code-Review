
package org.eclipse.jgit.storage.dfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.JGitText;

public final class DfsBlockCache {
	private static volatile DfsBlockCache cache;

	static {
		reconfigure(new DfsBlockCacheConfig());
	}

	public static void reconfigure(DfsBlockCacheConfig cfg) {
		DfsBlockCache nc = new DfsBlockCache(cfg);
		DfsBlockCache oc = cache;
		cache = nc;

		if (oc != null && oc.readAheadService != null)
			oc.readAheadService.shutdown();
	}

	public static DfsBlockCache getInstance() {
		return cache;
	}

	private final int tableSize;

	private final AtomicReferenceArray<HashEntry> table;

	private final Lock[] loadLocks;

	private final long maxBytes;

	private final int blockSize;

	private final int blockSizeShift;

	private final int readAheadLimit;

	private final ThreadPoolExecutor readAheadService;

	private final Map<DfsPackDescription

	private final AtomicLong statHit;

	private final AtomicLong statMiss;

	private volatile long statEvict;

	private final ReentrantLock clockLock;

	private Ref clockHand;

	private volatile long liveBytes;

	private DfsBlockCache(final DfsBlockCacheConfig cfg) {
		tableSize = tableSize(cfg);
		if (tableSize < 1)
			throw new IllegalArgumentException(JGitText.get().tSizeMustBeGreaterOrEqual1);

		table = new AtomicReferenceArray<HashEntry>(tableSize);
		loadLocks = new Lock[32];
		for (int i = 0; i < loadLocks.length; i++)
			loadLocks[i] = new Lock();

		int eb = (int) (tableSize * .1);
		if (64 < eb)
			eb = 64;
		else if (eb < 4)
			eb = 4;
		if (tableSize < eb)
			eb = tableSize;

		maxBytes = cfg.getBlockLimit();
		blockSize = cfg.getBlockSize();
		blockSizeShift = Integer.numberOfTrailingZeros(blockSize);

		clockHand = new Ref<Object>(null
		clockHand.next = clockHand;

		readAheadLimit = cfg.getReadAheadLimit();
		readAheadService = cfg.getReadAheadService();

		packCache = new HashMap<DfsPackDescription

		statHit = new AtomicLong();
		statMiss = new AtomicLong();
	}

	public long getCurrentSize() {
		return liveBytes;
	}

	public long getFillPercentage() {
		return getCurrentSize() * 100 / maxBytes;
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

	DfsPackFile getOrCreate(DfsPackDescription dsc
		synchronized (packCache) {
			DfsPackFile pack = packCache.get(dsc);
			if (pack != null && pack.invalid()) {
				packCache.remove(dsc);
				pack = null;
			}
			if (pack == null) {
				if (key == null)
					key = new DfsPackKey();
				pack = new DfsPackFile(this
				packCache.put(dsc
			}
			return pack;
		}
	}

	private int hash(int packHash
		return packHash + (int) (off >>> blockSizeShift);
	}

	int getBlockSize() {
		return blockSize;
	}

	private static int tableSize(final DfsBlockCacheConfig cfg) {
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
		if (v != null)
			return v;

		reserveSpace(blockSize);
		synchronized (lock(key
			HashEntry e2 = table.get(slot);
			if (e2 != e1) {
				v = scan(e2
				if (v != null) {
					creditSpace(blockSize);
					return v;
				}
			}

			statMiss.incrementAndGet();
			try {
				v = pack.readOneBlock(position
			} catch (IOException err) {
				creditSpace(blockSize);
				throw err;
			} catch (RuntimeException err) {
				creditSpace(blockSize);
				throw err;
			} catch (Error err) {
				creditSpace(blockSize);
				throw err;
			}
			if (position != v.start) {
				position = v.start;
				slot = slot(key
				e2 = table.get(slot);
			}

			Ref<DfsBlock> ref = new Ref<DfsBlock>(key
			ref.hot = true;
			for (;;) {
				HashEntry n = new HashEntry(clean(e2)
				if (table.compareAndSet(slot
					break;
				e2 = table.get(slot);
			}
			addToClock(ref
		}

		if (v.contains(pack.key
			return v;
		return getOrLoad(pack
	}

	@SuppressWarnings("unchecked")
	private void reserveSpace(int reserve) {
		clockLock.lock();
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
				statEvict++;
			} while (maxBytes < live);
			clockHand = prev;
		}
		liveBytes = live;
		clockLock.unlock();
	}

	private void creditSpace(int credit) {
		clockLock.lock();
		liveBytes -= credit;
		clockLock.unlock();
	}

	private void addToClock(Ref ref
		clockLock.lock();
		if (credit != 0)
			liveBytes -= credit;
		Ref ptr = clockHand;
		ref.next = ptr.next;
		ptr.next = ref;
		clockHand = ref;
		clockLock.unlock();
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
		synchronized (lock(key
			HashEntry e2 = table.get(slot);
			if (e2 != e1) {
				ref = scanRef(e2
				if (ref != null) {
					creditSpace(size);
					return ref;
				}
			}

			ref = new Ref<T>(key
			ref.hot = true;
			for (;;) {
				HashEntry n = new HashEntry(clean(e2)
				if (table.compareAndSet(slot
					break;
				e2 = table.get(slot);
			}
			addToClock(ref
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
		return val;
	}

	boolean readAhead(ReadableChannel rc
			long len
		if (!ctx.wantReadAhead() || readAheadLimit <= 0 || readAheadService == null)
			return false;

		int cap = readAheadLimit / size;
		long readAheadEnd = pos + readAheadLimit;
		List<ReadAheadTask.BlockFuture> blocks = new ArrayList<ReadAheadTask.BlockFuture>(cap);
		while (pos < readAheadEnd && pos < len) {
			long end = Math.min(pos + size
			if (!contains(key
				blocks.add(new ReadAheadTask.BlockFuture(key
			pos = end;
		}
		if (blocks.isEmpty())
			return false;

		ReadAheadTask task = new ReadAheadTask(this
		ReadAheadTask.TaskFuture t = new ReadAheadTask.TaskFuture(task);
		for (ReadAheadTask.BlockFuture b : blocks)
			b.setTask(t);
		readAheadService.execute(t);
		ctx.startedReadAhead(blocks);
		return true;
	}

	@SuppressWarnings("unchecked")
	private <T> T scan(HashEntry n
		for (; n != null; n = n.next) {
			Ref<T> r = n.ref;
			if (r.pack != pack || r.position != position)
				continue;
			T v = r.get();
			if (v == null)
				return null;
			statHit.incrementAndGet();
			return v;
		}
		return null;
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
		synchronized (packCache) {
			packCache.remove(pack.getPackDescription());
		}
	}

	private int slot(DfsPackKey pack
		return (hash(pack.hash
	}

	private Lock lock(DfsPackKey pack
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

	static final class Ref<T> {
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
	}

	private static final class Lock {
	}
}
