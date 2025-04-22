	/**
	 * Get the distinct result count for the given callable at the given
	 * concurrency.
	 *
	 * @param <T> the type of the callable
	 * @param num the concurrency
	 * @param callable the callable to invoke
	 * @return the number of distinct (by identity) results found
	 * @throws Throwable if an exception occurred in one of the invocations
	 */
	public static <T> int getDistinctResultCount(int num, Callable<T> callable)
		throws Throwable {
		IdentityHashMap<T, Object> found=new IdentityHashMap<T, Object>();
		Collection<SyncThread<T>> threads=getCompletedThreads(num, callable);
		for(SyncThread<T> s : threads) {
			found.put(s.getResult(), new Object());
		}
		return found.size();
	}
