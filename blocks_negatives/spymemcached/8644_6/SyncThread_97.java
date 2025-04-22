	/**
	 * Get a SyncThread that will call the given callable when the given
	 * barrier allows it past.
	 *
	 * @param b the barrier
	 * @param c the callable
	 */
	public SyncThread(CyclicBarrier b, Callable<T> c) {
		super("SyncThread");
		setDaemon(true);
		callable=c;
		barrier=b;
		latch=new CountDownLatch(1);
		start();
	}
