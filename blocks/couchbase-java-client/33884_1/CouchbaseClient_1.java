  @Override
  public <T> ReplicaGetFuture<CASValue<T>> asyncGetsFromReplica(final String key,
    final Transcoder<T> tc) {
    int discardedOps = 0;

    int bucketReplicaCount = cbConnFactory.getVBucketConfig().getReplicasCount();
    if (bucketReplicaCount == 0) {
      getLogger().debug("No replica configured for this bucket, trying to get "
        + "the document from active node only.");
    }

    VBucketNodeLocator locator = (VBucketNodeLocator) mconn.getLocator();
    List<Integer> actualReplicaIndexes = locator.getReplicaIndexes(key);

    final ReplicaGetFuture<CASValue<T>> replicaFuture = new ReplicaGetFuture<CASValue<T>>(
      operationTimeout, executorService);

    for(int index : actualReplicaIndexes) {
      final CountDownLatch latch = new CountDownLatch(1);
      final OperationFuture<CASValue<T>> rv =
        new OperationFuture<CASValue<T>>(key, latch, operationTimeout, executorService);
      Operation op = createOperationForReplicaGets(key, rv, replicaFuture,
        latch, tc, index, true);

      rv.setOperation(op);
      mconn.enqueueOperation(key, op);

      if (op.isCancelled()) {
        discardedOps++;
        getLogger().debug("Silently discarding replica get for key \""
          + key + "\" (cancelled).");
      } else {
        replicaFuture.addFutureToMonitor(rv);
      }

    }

    if (locator.hasActiveMaster(key)) {
      final CountDownLatch latch = new CountDownLatch(1);
      final OperationFuture<CASValue<T>> additionalActiveGet =
        new OperationFuture<CASValue<T>>(key, latch, operationTimeout, executorService);
      Operation op = createOperationForReplicaGets(key, additionalActiveGet,
        replicaFuture, latch, tc, 0, false);
      additionalActiveGet.setOperation(op);
      mconn.enqueueOperation(key, op);

      if (op.isCancelled()) {
        discardedOps++;
        getLogger().debug("Silently discarding replica (active) get for key \""
          + key + "\" (cancelled).");
      } else {
        replicaFuture.addFutureToMonitor(additionalActiveGet);
      }
    } else {
      discardedOps++;
    }

    if (discardedOps == actualReplicaIndexes.size() + 1) {
      throw new IllegalStateException("No replica get operation could be "
        + "dispatched because all operations have been cancelled.");
    }

    return replicaFuture;
  }

