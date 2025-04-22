   * Reconfigures the connected ViewNodes.
   *
   * When a reconfiguration event happens, new ViewNodes may need to be added
   * or old ones need to be removed from the current configuration. This method
   * takes care that those operations are performed in the correct order and
   * are executed in a thread-safe manner.
   *
   * @param bucket the bucket which has been rebalanced.
