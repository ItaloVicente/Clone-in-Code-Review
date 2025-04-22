	/**
	 * Get a collection of SyncThreads that all began as close to the
	 * same time as possible and have all completed.
	 * @param <T> the result type of the SyncThread
	 * @param num the number of concurrent threads to execute
	 * @param callable the thing to call
	 * @return the completed SyncThreads
	 * @throws InterruptedException if we're interrupted during join
	 */
	public static <T> Collection<SyncThread<T>> getCompletedThreads(
			int num, Callable<T> callable) throws InterruptedException {
		Collection<SyncThread<T>> rv=new ArrayList<SyncThread<T>>(num);
