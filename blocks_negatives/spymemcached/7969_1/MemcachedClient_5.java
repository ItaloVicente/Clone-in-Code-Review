	/**
	 * Getl with a single key. By default the maximum allowed timeout is 30
	 * seconds. Timeouts greater than this will be set to 30 seconds.
	 *
	 * @param key the key to get and lock
	 * @param exp the amount of time the lock should be valid for in seconds.
	 * @param tc the transcoder to serialize and unserialize value
	 * @return the result from the cache (null if there is none)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> CASValue<T> getAndLock(String key, int exp, Transcoder<T> tc) {
		try {
			return asyncGetAndLock(key, exp, tc).get(
					operationTimeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted waiting for value", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Exception waiting for value", e);
		} catch (TimeoutException e) {
			throw new OperationTimeoutException("Timeout waiting for value", e);
		}
	}

	/**
	 * Get and lock with a single key and decode using the default transcoder.
	 * By default the maximum allowed timeout is 30 seconds. Timeouts greater
	 * than this will be set to 30 seconds.
	 * @param key the key to get and lock
	 * @param exp the amount of time the lock should be valid for in seconds.
	 * @return the result from the cache (null if there is none)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public CASValue<Object> getAndLock(String key, int exp) {
		return getAndLock(key, exp, transcoder);
	}

