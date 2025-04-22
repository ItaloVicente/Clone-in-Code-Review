  /**
   * Unlock the given key synchronously from the cache with the default
   * transcoder.
   *
   * @param key the key to unlock
   * @param casId the CAS identifier
   * @return whether or not the operation was performed
   * @throws IllegalStateException in the rare circumstance where queue is too
   *           full to accept any more requests
   */
