
package org.eclipse.jgit.storage.dht;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.storage.dht.DhtReader.ChunkAndOffset;

public class ChunkCache {
	private static final Random rng = new Random();

	private static volatile ChunkCache cache;

	static {
		cache = new ChunkCache(new ChunkCacheConfig());
	}

	public static void reconfigure(ChunkCacheConfig cfg) {
		ChunkCache nc = new ChunkCache(cfg);
		cache = nc;
	}

	static ChunkCache get() {
		return cache;
	}

	private final ReferenceQueue<PackChunk> queue;

	private final int tableSize;

	private final AtomicLong clock;

	private final AtomicReferenceArray<Entry> table;

	private final Lock[] locks;

	private final ReentrantLock evictLock;

	private final int evictBatch;

	private final long maxBytes;

	private final AtomicLong openBytes;

	private ChunkCache(ChunkCacheConfig cfg) {
		tableSize = tableSize(cfg);
		final int lockCount = lockCount(cfg);
		if (tableSize < 0)
			throw new IllegalArgumentException();
		if (lockCount < 0)
			throw new IllegalArgumentException();

		queue = new ReferenceQueue<PackChunk>();
		clock = new AtomicLong(1);
		table = new AtomicReferenceArray<Entry>(tableSize);
		locks = new Lock[lockCount];
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

		maxBytes = cfg.getChunkCacheLimit();
		openBytes = new AtomicLong();
	}

	long getOpenBytes() {
		return openBytes.get();
	}

	private Ref createRef(ChunkKey key
		final Ref ref = new Ref(key
		openBytes.addAndGet(ref.size);
		return ref;
	}

	private void clear(Ref ref) {
		openBytes.addAndGet(-ref.size);
	}

	private boolean isFull() {
		return maxBytes < openBytes.get();
	}

	private static int tableSize(ChunkCacheConfig cfg) {
		final int csz = 1 * ChunkCacheConfig.MiB;
		final long limit = cfg.getChunkCacheLimit();
		if (limit == 0)
			return 0;
		if (csz <= 0)
			throw new IllegalArgumentException();
		if (limit < csz)
			throw new IllegalArgumentException();
		return (int) Math.min(5 * (limit / csz) / 2
	}

	private static int lockCount(ChunkCacheConfig cfg) {
		if (cfg.getChunkCacheLimit() == 0)
			return 0;
		return 32;
	}

	PackChunk get(ChunkKey chunkKey) {
		if (tableSize == 0)
			return null;
		return scan(table.get(slot(chunkKey))
	}

	ChunkAndOffset find(RepositoryKey repo

		for (int slot = 0; slot < tableSize; slot++) {
			for (Entry e = table.get(slot); e != null; e = e.next) {
				PackChunk chunk = e.ref.get();
				if (chunk != null) {
					int pos = chunk.findOffset(repo
					if (0 <= pos) {
						hit(e.ref);
						return new ChunkAndOffset(chunk
					}
				}
			}
		}
		return null;
	}

	PackChunk put(PackChunk chunk) {
		if (tableSize == 0)
			return chunk;

		final ChunkKey chunkKey = chunk.getChunkKey();
		final int slot = slot(chunkKey);
		final Entry e1 = table.get(slot);
		PackChunk v = scan(e1
		if (v != null)
			return v;

		synchronized (lock(chunkKey)) {
			Entry e2 = table.get(slot);
			if (e2 != e1) {
				v = scan(e2
				if (v != null)
					return v;
			}

			v = chunk;
			final Ref ref = createRef(chunkKey
			hit(ref);
			for (;;) {
				final Entry n = new Entry(clean(e2)
				if (table.compareAndSet(slot
					break;
				e2 = table.get(slot);
			}
		}

		if (evictLock.tryLock()) {
			try {
				gc();
				evict();
			} finally {
				evictLock.unlock();
			}
		}

		return v;
	}

	private PackChunk scan(Entry n
		for (; n != null; n = n.next) {
			Ref r = n.ref;
			if (r.chunk.equals(chunk)) {
				PackChunk v = r.get();
				if (v != null) {
					hit(r);
					return v;
				}
				n.kill();
				break;
			}
		}
		return null;
	}

	private void hit(final Ref r) {
		final long c = clock.get();
		clock.compareAndSet(c
		r.lastAccess = c;
	}

	private void evict() {
		while (isFull()) {
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
				old.kill();
				gc();
				final Entry e1 = table.get(slot);
				table.compareAndSet(slot
			}
		}
	}

	private void gc() {
		Ref r;
		while ((r = (Ref) queue.poll()) != null) {
			if (r.canClear()) {
				clear(r);

				boolean found = false;
				final int s = slot(r.chunk);
				final Entry e1 = table.get(s);
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

	private int slot(ChunkKey chunk) {
		return (chunk.hashCode() >>> 1) % tableSize;
	}

	private Lock lock(ChunkKey chunk) {
		return locks[(chunk.hashCode() >>> 1) % locks.length];
	}

	private static Entry clean(Entry top) {
		while (top != null && top.dead) {
			top.ref.enqueue();
			top = top.next;
		}
		if (top == null)
			return null;
		final Entry n = clean(top.next);
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

	private static class Ref extends SoftReference<PackChunk> {
		final ChunkKey chunk;

		final int size;

		long lastAccess;

		private boolean cleared;

		Ref(ChunkKey chunk
			super(v
			this.chunk = chunk;
			this.size = v.getTotalSize();
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
