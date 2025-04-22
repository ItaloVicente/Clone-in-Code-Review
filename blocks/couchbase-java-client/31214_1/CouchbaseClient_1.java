
    final CountDownLatch latch = new CountDownLatch(1);
    final ObserveFuture<Boolean> observeFuture = new ObserveFuture<Boolean>(
      key, latch, operationTimeout, executorService);


