	private final int blockSize;

	/** As {@link #blockSize} is a power of 2, bits to shift for a / blockSize. */
	private final int blockSizeShift;

	/** Cache of pack files, indexed by description. */
	private final Map<DfsPackDescription, DfsPackFile> packCache;

	/** View of pack files in the pack cache. */
	private final Collection<DfsPackFile> packFiles;

	/** Number of times a block was found in the cache. */
	private final AtomicLong statHit;

	/** Number of times a block was not found, and had to be loaded. */
	private final AtomicLong statMiss;

	/** Number of blocks evicted due to cache being full. */
	private volatile long statEvict;

	/** Protects the clock and its related data. */
	private final ReentrantLock clockLock;

	/** Current position of the clock. */
	private Ref clockHand;

	/** Number of bytes currently loaded in the cache. */
	private volatile long liveBytes;

	@SuppressWarnings("unchecked")
	private DfsBlockCache(final DfsBlockCacheConfig cfg) {
		tableSize = tableSize(cfg);
		if (tableSize < 1)
			throw new IllegalArgumentException(JGitText.get().tSizeMustBeGreaterOrEqual1);

		table = new AtomicReferenceArray<>(tableSize);
		loadLocks = new ReentrantLock[cfg.getConcurrencyLevel()];
		for (int i = 0; i < loadLocks.length; i++)
			loadLocks[i] = new ReentrantLock(true /* fair */);

		maxBytes = cfg.getBlockLimit();
		maxStreamThroughCache = (long) (maxBytes * cfg.getStreamRatio());
		blockSize = cfg.getBlockSize();
		blockSizeShift = Integer.numberOfTrailingZeros(blockSize);

		clockLock = new ReentrantLock(true /* fair */);
		clockHand = new Ref<>(new DfsPackKey(), -1, 0, null);
		clockHand.next = clockHand;

		packCache = new ConcurrentHashMap<>(
				16, 0.75f, 1);
		packFiles = Collections.unmodifiableCollection(packCache.values());

		statHit = new AtomicLong();
		statMiss = new AtomicLong();
	}

	boolean shouldCopyThroughCache(long length) {
		return length <= maxStreamThroughCache;
	}

	/** @return total number of bytes in the cache. */
	public long getCurrentSize() {
		return liveBytes;
	}

	/** @return 0..100, defining how full the cache is. */
	public long getFillPercentage() {
		return getCurrentSize() * 100 / maxBytes;
	}

	/** @return number of requests for items in the cache. */
	public long getHitCount() {
		return statHit.get();
	}

	/** @return number of requests for items not in the cache. */
	public long getMissCount() {
		return statMiss.get();
	}

	/** @return total number of requests (hit + miss). */
	public long getTotalRequestCount() {
		return getHitCount() + getMissCount();
	}

	/** @return 0..100, defining number of cache hits. */
	public long getHitRatio() {
		long hits = statHit.get();
		long miss = statMiss.get();
		long total = hits + miss;
		if (total == 0)
			return 0;
		return hits * 100 / total;
	}

	/** @return number of evictions performed due to cache being full. */
	public long getEvictions() {
		return statEvict;
	}

	/**
	 * Get the pack files stored in this cache.
	 *
	 * @return a collection of pack files, some of which may not actually be
	 *             present; the caller should check the pack's cached size.
	 */
	public Collection<DfsPackFile> getPackFiles() {
		return packFiles;
	}

	DfsPackFile getOrCreate(DfsPackDescription dsc, DfsPackKey key) {

		DfsPackFile pack = packCache.get(dsc);
		if (pack != null && !pack.invalid()) {
			return pack;
		}

		return packCache.compute(dsc, (k, v) -> {
			} else {
				return new DfsPackFile(
						this, dsc, key != null ? key : new DfsPackKey());
			}
		});
	}

	private int hash(int packHash, long off) {
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
		return (int) Math.min(5 * (limit / wsz) / 2, Integer.MAX_VALUE);
	}

	/**
	 * Lookup a cached object, creating and loading it if it doesn't exist.
	 *
	 * @param pack
	 *            the pack that "contains" the cached object.
	 * @param position
	 *            offset within <code>pack</code> of the object.
	 * @param ctx
	 *            current thread's reader.
	 * @return the object reference.
	 * @throws IOException
	 *             the reference was not in the cache and could not be loaded.
	 */
	DfsBlock getOrLoad(DfsPackFile pack, long position, DfsReader ctx)
			throws IOException {
		final long requestedPosition = position;
		position = pack.alignToBlock(position);

		DfsPackKey key = pack.key;
		int slot = slot(key, position);
		HashEntry e1 = table.get(slot);
		DfsBlock v = scan(e1, key, position);
		if (v != null) {
			statHit.incrementAndGet();
			return v;
		}

		reserveSpace(blockSize);
		ReentrantLock regionLock = lockFor(key, position);
		regionLock.lock();
		try {
			HashEntry e2 = table.get(slot);
			if (e2 != e1) {
				v = scan(e2, key, position);
				if (v != null) {
					statHit.incrementAndGet();
					creditSpace(blockSize);
					return v;
				}
			}

			statMiss.incrementAndGet();
			boolean credit = true;
			try {
				v = pack.readOneBlock(position, ctx);
				credit = false;
			} finally {
				if (credit)
					creditSpace(blockSize);
			}
			if (position != v.start) {
				position = v.start;
				slot = slot(key, position);
				e2 = table.get(slot);
			}

			key.cachedSize.addAndGet(v.size());
			Ref<DfsBlock> ref = new Ref<>(key, position, v.size(), v);
			ref.hot = true;
			for (;;) {
				HashEntry n = new HashEntry(clean(e2), ref);
				if (table.compareAndSet(slot, e2, n))
					break;
				e2 = table.get(slot);
			}
			addToClock(ref, blockSize - v.size());
		} finally {
			regionLock.unlock();
		}

		if (v.contains(pack.key, requestedPosition))
			return v;
		return getOrLoad(pack, requestedPosition, ctx);
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
	private void addToClock(Ref ref, int credit) {
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
