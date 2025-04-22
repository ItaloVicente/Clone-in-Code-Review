	/**
	 * Gets and locks the given key asynchronously. By default the maximum allowed
	 * timeout is 30 seconds. Timeouts greater than this will be set to 30 seconds.
	 *
	 * @param key the key to fetch and lock
	 * @param exp the amount of time the lock should be valid for in seconds.
	 * @param tc the transcoder to serialize and unserialize value
	 * @return a future that will hold the return value of the fetch
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> OperationFuture<CASValue<T>> asyncGetAndLock(final String key, int exp,
			final Transcoder<T> tc) {
		final CountDownLatch latch=new CountDownLatch(1);
		final OperationFuture<CASValue<T>> rv=
			new OperationFuture<CASValue<T>>(key, latch, operationTimeout);

		Operation op=opFact.getl(key, exp,
				new GetlOperation.Callback() {
			private CASValue<T> val=null;
			public void receivedStatus(OperationStatus status) {
				rv.set(val, status);
			}
			public void gotData(String k, int flags, long cas, byte[] data) {
				assert key.equals(k) : "Wrong key returned";
				assert cas > 0 : "CAS was less than zero:  " + cas;
				val=new CASValue<T>(cas, tc.decode(
					new CachedData(flags, data, tc.getMaxSize())));
			}
			public void complete() {
				latch.countDown();
			}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

	/**
	 * Get and lock the given key asynchronously and decode with the default
	 * transcoder. By default the maximum allowed timeout is 30 seconds.
	 * Timeouts greater than this will be set to 30 seconds.
	 *
	 * @param key the key to fetch and lock
	 * @param exp the amount of time the lock should be valid for in seconds.
	 * @return a future that will hold the return value of the fetch
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<CASValue<Object>> asyncGetAndLock(final String key, int exp) {
		return asyncGetAndLock(key, exp, transcoder);
	}

