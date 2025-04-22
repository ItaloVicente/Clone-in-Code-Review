  /**
   * Set a value with durability options and no TTL.
   *
   * This is a shorthand method so that you only need to provide a
   * PersistTo value if you don't care if the value is already replicated.
   * A PersistTo.TWO durability setting implies a replication to at least
   * one node.
   *
   * For more information on how the durability options work, see the docblock
   * for the set() operation with both PersistTo and ReplicateTo settings.
   *
   * @param key the key to store.
   * @param value the value of the key.
   * @return the future result of the set operation.
   */
