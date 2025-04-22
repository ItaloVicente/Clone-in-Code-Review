
package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.storage.file.WindowCacheConfig;

public class WindowCache {
	private static final int bits(int newSize) {
		if (newSize < 4096)
			throw new IllegalArgumentException(JGitText.get().invalidWindowSize);
		if (Integer.bitCount(newSize) != 1)
			throw new IllegalArgumentException(JGitText.get().windowSizeMustBePowerOf2);
		return Integer.numberOfTrailingZeros(newSize);
	}

	private static final Random rng = new Random();

	private static volatile WindowCache cache;

	private static volatile int streamFileThreshold;

	static {
		reconfigure(new WindowCacheConfig());
	}

	@Deprecated
	public static void reconfigure(WindowCacheConfig cfg) {
		final WindowCache nc = new WindowCache(cfg);
		final WindowCache oc = cache;
		if (oc != null)
			oc.removeAll();
		cache = nc;
		streamFileThreshold = cfg.getStreamFileThreshold();
		DeltaBaseCache.reconfigure(cfg);
	}

	static int getStreamFileThreshold() {
		return streamFileThreshold;
	}

	public static WindowCache getInstance() {
		return cache;
	}

	static final ByteWindow get(PackFile pack
			throws IOException {
		final WindowCache c = cache;
		final ByteWindow r = c.getOrLoad(pack
		if (c != cache) {
			c.removeAll();
		}
		return r;
	}

	static final void purge(PackFile pack) {
		cache.removeAll(pack);
	}

	private final ReferenceQueue<ByteWindow> queue;

	private final int tableSize;

	private final AtomicLong clock;

	private final AtomicReferenceArray<Entry> table;

	private final Lock[] locks;

	private final ReentrantLock evictLock;

	private final int evictBatch;

	private final int maxFiles;

	private final long maxBytes;

	private final boolean mmap;

	private final int windowSizeShift;

	private final int windowSize;

	private final AtomicInteger openFiles;

	private final AtomicLong openBytes;

	private WindowCache(WindowCacheConfig cfg) {
		tableSize = tableSize(cfg);
		final int lockCount = lockCount(cfg);
		if (tableSize < 1)
			throw new IllegalArgumentException(JGitText.get().tSizeMustBeGreaterOrEqual1);
		if (lockCount < 1)
			throw new IllegalArgumentException(JGitText.get().lockCountMustBeGreaterOrEqual1);

		queue = new ReferenceQueue<>();
		clock = new AtomicLong(1);
		table = new AtomicReferenceArray<>(tableSize);
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

		maxFiles = cfg.getPackedGitOpenFiles();
		maxBytes = cfg.getPackedGitLimit();
		mmap = cfg.isPackedGitMMAP();
		windowSizeShift = bits(cfg.getPackedGitWindowSize());
		windowSize = 1 << windowSizeShift;

		openFiles = new AtomicInteger();
		openBytes = new AtomicLong();

		if (maxFiles < 1)
			throw new IllegalArgumentException(JGitText.get().openFilesMustBeAtLeast1);
		if (maxBytes < windowSize)
			throw new IllegalArgumentException(JGitText.get().windowSizeMustBeLesserThanLimit);
	}

	public int getOpenFiles() {
		return openFiles.get();
	}

	public long getOpenBytes() {
		return openBytes.get();
	}

	private int hash(int packHash
		return packHash + (int) (off >>> windowSizeShift);
	}

	private ByteWindow load(PackFile pack
			throws IOException {
		if (pack.beginWindowCache())
			openFiles.incrementAndGet();
		try {
			if (mmap)
				return pack.mmap(offset
			return pack.read(offset
		} catch (IOException | RuntimeException | Error e) {
			close(pack);
			throw e;
		}
	}

	private Ref createRef(PackFile p
		final Ref ref = new Ref(p
		openBytes.addAndGet(ref.size);
		return ref;
	}

	private void clear(Ref ref) {
		openBytes.addAndGet(-ref.size);
		close(ref.pack);
	}

	private void close(PackFile pack) {
		if (pack.endWindowCache())
			openFiles.decrementAndGet();
	}

	private boolean isFull() {
		return maxFiles < openFiles.get() || maxBytes < openBytes.get();
	}

	private long toStart(long offset) {
		return (offset >>> windowSizeShift) << windowSizeShift;
	}

	private static int tableSize(WindowCacheConfig cfg) {
		final int wsz = cfg.getPackedGitWindowSize();
		final long limit = cfg.getPackedGitLimit();
		if (wsz <= 0)
			throw new IllegalArgumentException(JGitText.get().invalidWindowSize);
		if (limit < wsz)
			throw new IllegalArgumentException(JGitText.get().windowSizeMustBeLesserThanLimit);
		return (int) Math.min(5 * (limit / wsz) / 2
	}

	private static int lockCount(WindowCacheConfig cfg) {
		return Math.max(cfg.getPackedGitOpenFiles()
	}

	private ByteWindow getOrLoad(PackFile pack
			throws IOException {
		final int slot = slot(pack
		final Entry e1 = table.get(slot);
		ByteWindow v = scan(e1
		if (v != null)
			return v;

		synchronized (lock(pack
			Entry e2 = table.get(slot);
			if (e2 != e1) {
				v = scan(e2
				if (v != null)
					return v;
			}

			v = load(pack
			final Ref ref = createRef(pack
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

	private ByteWindow scan(Entry n
		for (; n != null; n = n.next) {
			final Ref r = n.ref;
			if (r.pack == pack && r.position == position) {
				final ByteWindow v = r.get();
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

	private void hit(Ref r) {
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

	private void removeAll() {
		for (int s = 0; s < tableSize; s++) {
			Entry e1;
			do {
				e1 = table.get(s);
				for (Entry e = e1; e != null; e = e.next)
					e.kill();
			} while (!table.compareAndSet(s
		}
		gc();
	}

	private void removeAll(PackFile pack) {
		for (int s = 0; s < tableSize; s++) {
			final Entry e1 = table.get(s);
			boolean hasDead = false;
			for (Entry e = e1; e != null; e = e.next) {
				if (e.ref.pack == pack) {
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
		while ((r = (Ref) queue.poll()) != null) {
			clear(r);

			final int s = slot(r.pack
			final Entry e1 = table.get(s);
			for (Entry n = e1; n != null; n = n.next) {
				if (n.ref == r) {
					n.dead = true;
					table.compareAndSet(s
					break;
				}
			}
		}
	}

	private int slot(PackFile pack
		return (hash(pack.hash
	}

	private Lock lock(PackFile pack
		return locks[(hash(pack.hash
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

		Entry(Entry n
			next = n;
			ref = r;
		}

		final void kill() {
			dead = true;
			ref.enqueue();
		}
	}

	private static class Ref extends SoftReference<ByteWindow> {
		final PackFile pack;

		final long position;

		final int size;

		long lastAccess;

		protected Ref(final PackFile pack
				final ByteWindow v
			super(v
			this.pack = pack;
			this.position = position;
			this.size = v.size();
		}
	}

	private static final class Lock {
	}
}
