  /**
   * Replace a value with durability options with no TTL
   *
   * This method allows you to express durability at the replication level
   * only and is the functional equivalent of PersistTo.ZERO.
   *
   * A common use case for this would be to achieve good insert-performance
   * and at the same time making sure that the data is at least replicated
   * to the given amount of nodes to provide a better level of data safety.
   *
   * For more information on how the durability options work, see the docblock
   * for the replace() operation with both PersistTo and ReplicateTo settings.
   *
   * @param key the key to store.
   * @param value the value of the key.
   * @param rep the amount of nodes the item should be replicated to before
   *            returning.
   * @return the future result of the replace operation.
   */
