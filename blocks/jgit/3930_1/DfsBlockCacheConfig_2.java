
package org.eclipse.jgit.storage.dfs;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.JGitText;

public final class DfsBlockCache {
	private static final Random rng = new Random();

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

	private final ReferenceQueue<Object> deadBlockQueue;

	private final int tableSize;

	private final AtomicLong clock;

	private final AtomicReferenceArray<Entry> table;

	private final Lock[] locks;

	private final ReentrantLock evictLock;

	private final int evictBatch;

	private final long maxBytes;

	private final int blockSize;

	private final int blockSizeShift;

	private final int readAheadLimit;

	private final ThreadPoolExecutor readAheadService;

	private final Map<DfsPackDescription

	private final AtomicLong openBytes;

	private final AtomicLong statHit;

	private final AtomicLong statMiss;

	private final AtomicLong statEvict;

	private final AtomicLong statGC;

	private DfsBlockCache(final DfsBlockCacheConfig cfg) {
		tableSize = tableSize(cfg);
		if (tableSize < 1)
			throw new IllegalArgumentException(JGitText.get().tSizeMustBeGreaterOrEqual1);

		deadBlockQueue = new ReferenceQueue<Object>();
		clock = new AtomicLong(1);
		table = new AtomicReferenceArray<Entry>(tableSize);
		locks = new Lock[32];
		for (int i = 0; i < locks.length; i++)
			locks[i] = new Lock();
		evictLock = new ReentrantLock();

		int eb = (int) (tableSize * .1);
		if (64 < eb)
			eb = 64;
		else if (eb < 4)
			eb = 4;
		if (tableSize < eb)
			eb = tableSize;
		evictBatch = eb;

		maxBytes = cfg.getBlockLimit();
		blockSize = cfg.getBlockSize();
		blockSizeShift = Integer.numberOfTrailingZeros(blockSize);

		readAheadLimit = cfg.getReadAheadLimit();
		readAheadService = cfg.getReadAheadService();

		packCache = new HashMap<DfsPackDescription

		openBytes = new AtomicLong();
		statHit = new AtomicLong();
		statMiss = new AtomicLong();
		statEvict = new AtomicLong();
		statGC = new AtomicLong();
	}

	public long getCurrentSize() {
		return openBytes.get();
	}

	public long getFillPercentage() {
		return getCurrentSize() * 100 / maxBytes;
	}

	public long getHitRatio() {
		long hits = statHit.get();
		long miss = statMiss.get();
		long tot = hits + miss;
		if (tot == 0)
			return 0;
		return hits * 100 / tot;
	}

	public long getEvictions() {
		return statEvict.get();
	}

	public long getEvictionsCausedByGC() {
		return statGC.get();
	}

	DfsPackFile getOrCreate(DfsPackDescription dsc
		synchronized (packCache) {
			DfsPackFile pack = packCache.get(dsc);
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

	private <T> Ref<T> createRef(DfsPackKey pack
		Ref<T> ref = new Ref<T>(pack
		openBytes.addAndGet(ref.size);
		return ref;
	}

	private void clear(final Ref ref) {
		openBytes.addAndGet(-ref.size);
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
		Entry e1 = table.get(slot);
		DfsBlock v = scan(e1
		if (v != null) {
			statHit.incrementAndGet();
			return v;
		}

		synchronized (lock(key
			Entry e2 = table.get(slot);
			if (e2 != e1) {
				v = scan(e2
				if (v != null) {
					statHit.incrementAndGet();
					return v;
				}
			}

			statMiss.incrementAndGet();
			v = pack.readOneBlock(position
			if (position != v.start) {
				position = v.start;
				slot = slot(key
				e2 = table.get(slot);
			}

			Ref<DfsBlock> ref = createRef(key
			hit(ref);
			for (;;) {
				Entry n = new Entry(clean(e2)
				if (table.compareAndSet(slot
					break;
				e2 = table.get(slot);
			}
		}
		evictIfNecessary();

		if (v.contains(pack.key
			return v;
		return getOrLoad(pack
	}

	void put(DfsBlock v) {
		put(v.pack
	}

	<T> Ref<T> put(DfsPackKey key
		int slot = slot(key
		Entry e1 = table.get(slot);
		Ref<T> ref = scanRef(e1
		if (ref != null)
			return ref;

		synchronized (lock(key
			Entry e2 = table.get(slot);
			if (e2 != e1) {
				ref = scanRef(e2
				if (ref != null)
					return ref;
			}

			ref = createRef(key
			hit(ref);
			for (;;) {
				Entry n = new Entry(clean(e2)
				if (table.compareAndSet(slot
					break;
				e2 = table.get(slot);
			}
		}
		evictIfNecessary();
		return ref;
	}

	boolean contains(DfsPackKey key
		return get(key
	}

	<T> T get(DfsPackKey key
		T b = scan(table.get(slot(key
		if (b != null)
			statHit.incrementAndGet();
		return b;
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
	private <T> T scan(Entry n
		for (; n != null; n = n.next) {
			Ref<T> r = n.ref;
			if (r.pack == pack && r.position == position) {
				T v = r.get();
				if (v != null) {
					hit(r);
					return v;
				}
				if (!n.dead)
					statGC.incrementAndGet();
				n.kill();
				break;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private <T> Ref<T> scanRef(Entry n
		for (; n != null; n = n.next) {
			Ref<T> r = n.ref;
			if (r.pack == pack && r.position == position) {
				if (r.get() != null)
					return r;
				if (!n.dead)
					statGC.incrementAndGet();
				n.kill();
				break;
			}
		}
		return null;
	}

	<T> void hit(final Ref<T> r) {
		long c = clock.get();
		clock.compareAndSet(c
		r.lastAccess = c;
	}

	private void evictIfNecessary() {
		if (maxBytes < openBytes.get() && evictLock.tryLock()) {
			try {
				gc();
				if (maxBytes < openBytes.get())
					evict();
			} finally {
				evictLock.unlock();
			}
		}
	}

	private void evict() {
		long target = (long) (maxBytes * 0.9);
		while (target < openBytes.get()) {
			int ptr = rng.nextInt(tableSize);
			Entry old = null;
			int slot = 0;
			for (int b = evictBatch - 1; b >= 0; b--
				if (tableSize <= ptr)
					ptr = 0;
				for (Entry e = table.get(ptr); e != null; e = e.next) {
					if (e.dead)
						continue;
					if (old == null || e.ref.lastAccess < old.ref.lastAccess) {
						old = e;
						slot = ptr;
					}
				}
			}
			if (old != null) {
				statEvict.incrementAndGet();
				old.kill();
				gc();
				Entry e1 = table.get(slot);
				table.compareAndSet(slot
			}
		}
	}

	void remove(DfsPackFile pack) {
		synchronized (packCache) {
			packCache.remove(pack.getPackDescription());
		}

		DfsPackKey key = pack.key;
		for (int s = 0; s < tableSize; s++) {
			Entry e1 = table.get(s);
			boolean hasDead = false;
			for (Entry e = e1; e != null; e = e.next) {
				if (e.ref.pack == key) {
					e.kill();
					hasDead = true;
				} else if (e.dead)
					hasDead = true;
			}
			if (hasDead)
				table.compareAndSet(s
		}
		gc();
	}

	private void gc() {
		Ref r;
		while ((r = (Ref) deadBlockQueue.poll()) != null) {
			if (r.canClear()) {
				clear(r);

				boolean found = false;
				int s = slot(r.pack
				Entry e1 = table.get(s);
				for (Entry n = e1; n != null; n = n.next) {
					if (n.ref == r) {
						n.dead = true;
						found = true;
						break;
					}
				}
				if (found)
					table.compareAndSet(s
			}
		}
	}

	private int slot(DfsPackKey pack
		return (hash(pack.hash
	}

	private Lock lock(DfsPackKey pack
		return locks[(hash(pack.hash
	}

	private static Entry clean(Entry top) {
		while (top != null && top.dead) {
			top.ref.enqueue();
			top = top.next;
		}
		if (top == null)
			return null;
		Entry n = clean(top.next);
		return n == top.next ? top : new Entry(n
	}

	private static class Entry {
		final Entry next;

		final Ref ref;

		volatile boolean dead;

		Entry(final Entry n
			next = n;
			ref = r;
		}

		final void kill() {
			dead = true;
			ref.enqueue();
		}
	}

	static class Ref<T> extends SoftReference<T> {
		final DfsPackKey pack;

		final long position;

		final int size;

		long lastAccess;

		private boolean cleared;

		Ref(DfsPackKey pack
				ReferenceQueue<Object> queue) {
			super(v
			this.pack = pack;
			this.position = position;
			this.size = size;
		}

		final synchronized boolean canClear() {
			if (cleared)
				return false;
			cleared = true;
			return true;
		}
	}

	private static final class Lock {
	}
}
