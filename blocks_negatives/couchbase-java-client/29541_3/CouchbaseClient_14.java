  /**
   * Get and lock the given key asynchronously and decode with the default
   * transcoder. By default the maximum allowed timeout is 30 seconds. Timeouts
   * greater than this will be set to 30 seconds.
   *
   * @param key the key to fetch and lock
   * @param exp the amount of time the lock should be valid for in seconds.
   * @return a future that will hold the return value of the fetch
   * @throws IllegalStateException in the rare circumstance where queue is too
   *           full to accept any more requests
   */
