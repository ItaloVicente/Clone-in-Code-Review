	private final ReferenceQueue<ByteWindow> queue;

	private final int tableSize;

	private final AtomicLong clock;

	private final AtomicReferenceArray<Entry> table;

	private final Lock[] locks;

	private final ReentrantLock evictLock;

	private final int evictBatch;

