	/** @return the currently active DfsBlockCache. */
	public static DfsBlockCache getInstance() {
		return cache;
	}

	/** Number of entries in {@link #table}. */
	private final int tableSize;

	/** Hash bucket directory; entries are chained below. */
	private final AtomicReferenceArray<HashEntry> table;

	/** Locks to prevent concurrent loads for same (PackFile,position). */
	private final ReentrantLock[] loadLocks;

	/** Maximum number of bytes the cache should hold. */
	private final long maxBytes;

	/** Pack files smaller than this size can be copied through the cache. */
	private final long maxStreamThroughCache;

