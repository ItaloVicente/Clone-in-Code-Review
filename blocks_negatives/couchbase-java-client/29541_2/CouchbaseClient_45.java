  /**
   * Replace a value with durability options.
   *
   * This is a shorthand method so that you only need to provide a
   * PersistTo value if you don't care if the value is already replicated.
   * A PersistTo.TWO durability setting implies a replication to at least
   * one node.
   *
   * For more information on how the durability options work, see the docblock
   * for the replace() operation with both PersistTo and ReplicateTo settings.
   *
   * @param key the key to store.
   * @param exp the expiry value to use.
   * @param value the value of the key.
   * @param req the amount of nodes the item should be persisted to before
   *            returning.
   * @return the future result of the replace operation.
   */
