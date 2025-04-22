	/**
	 * A ref database storing all refs in-memory.
	 * <p>
	 * This class is protected (and not private) to facilitate testing using
	 * subclasses of InMemoryRepository.
	 */
    protected class MemRefDatabase extends DfsRefDatabase {
		private final ConcurrentMap<String, Ref> refs = new ConcurrentHashMap<>();
		private final ReadWriteLock lock = new ReentrantReadWriteLock(true /* fair */);
