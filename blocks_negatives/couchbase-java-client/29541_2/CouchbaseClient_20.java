  /**
   * Get and lock with a single key and decode using the default transcoder. By
   * default the maximum allowed timeout is 30 seconds. Timeouts greater than
   * this will be set to 30 seconds.
   *
   * @param key the key to get and lock
   * @param exp the amount of time the lock should be valid for in seconds.
   * @return the result from the cache (null if there is none)
   * @throws OperationTimeoutException if the global operation timeout is
   *           exceeded
   * @throws IllegalStateException in the rare circumstance where queue is too
   *           full to accept any more requests
   */
