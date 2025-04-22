  /**
   * Get a document from a replica node.
   *
   * This method allows you to explicitly load a document from a replica
   * instead of the master node.
   *
   * This command only works on couchbase type buckets.
   *
   * @param key the key to fetch.
   * @return the fetched document or null when no document available.
   * @throws RuntimeException when less replicas available then in the index
   *         argument defined.
   */
