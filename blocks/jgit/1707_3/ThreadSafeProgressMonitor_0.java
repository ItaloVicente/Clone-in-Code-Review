	private final Thread mainThread;

	private final AtomicInteger workers;

	private final AtomicInteger pendingUpdates;

	private final Semaphore process;

