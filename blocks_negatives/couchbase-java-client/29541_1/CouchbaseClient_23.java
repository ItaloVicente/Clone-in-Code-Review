  /**
   * Unlock the given key synchronously from the cache.
   *
   * @param key the key to unlock
   * @param casId the CAS identifier
   * @param tc the transcoder to serialize and unserialize value
   * @return whether or not the operation was performed
   * @throws IllegalStateException in the rare circumstance where queue is too
   *           full to accept any more requests
   * @throws CancellationException if operation was canceled
   */
