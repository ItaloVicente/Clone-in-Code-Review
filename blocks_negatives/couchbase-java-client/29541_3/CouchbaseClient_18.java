  /**
   * Get a document from a replica node asynchronously.
   *
   * This method allows you to explicitly load a document from a replica
   * instead from the master node. This command only works on couchbase
   * type buckets.
   *
   * @param key the key to fetch.
   * @param tc a custom document transcoder.
   * @return a future containing the fetched document or null when no document
   *         available.
   * @throws RuntimeException when less replicas available then in the index
   *         argument defined.
   */
