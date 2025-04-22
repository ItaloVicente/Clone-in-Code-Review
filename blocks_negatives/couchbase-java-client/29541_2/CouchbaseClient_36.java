  /**
   * Add a value with durability options.
   *
   * To make sure that a value is stored the way you want it to in the
   * cluster, you can use the PersistTo and ReplicateTo arguments. The
   * operation will block until the desired state is satisfied or
   * otherwise an exception is raised. There are many reasons why this could
   * happen, the more frequent ones are as follows:
   *
   * - The given replication settings are invalid.
   * - The operation could not be completed within the timeout.
   * - Something goes wrong and a cluster failover is triggered.
   *
   * The client does not attempt to guarantee the given durability
   * constraints, it just reports whether the operation has been completed
   * or not. If it is not achieved, it is the responsibility of the
   * application code using this API to re-retrieve the items to verify
   * desired state, redo the operation or both.
   *
   * Note that even if an exception during the observation is raised,
   * this doesn't mean that the operation has failed. A normal add()
   * operation is initiated and after the OperationFuture has returned,
   * the key itself is observed with the given durability options (watch
   * out for Observed*Exceptions) in this case.
   *
   * @param key the key to store.
   * @param exp the expiry value to use.
   * @param value the value of the key.
   * @param req the amount of nodes the item should be persisted to before
   *            returning.
   * @param rep the amount of nodes the item should be replicated to before
   *            returning.
   * @return the future result of the add operation.
   */
