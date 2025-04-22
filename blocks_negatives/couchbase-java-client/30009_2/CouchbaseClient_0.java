    final CountDownLatch latch = new CountDownLatch(1);
    final GetFuture<T> additionalActiveGet = new GetFuture<T>(latch, operationTimeout, key,
      executorService);
    Operation op = createOperationForReplicaGet(key, additionalActiveGet,
      replicaFuture, latch, tc, 0, false);
    additionalActiveGet.setOperation(op);
    mconn.enqueueOperation(key, op);
