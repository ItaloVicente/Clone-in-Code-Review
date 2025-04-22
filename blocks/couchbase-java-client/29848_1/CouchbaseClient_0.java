    final CountDownLatch latch = new CountDownLatch(1);
    final GetFuture<T> additionalActiveGet = new GetFuture<T>(latch, operationTimeout, key,
      executorService);
    Operation op = opFact.get(key, new GetOperation.Callback() {
        private Future<T> val = null;

        @Override
        public void receivedStatus(OperationStatus status) {
          additionalActiveGet.set(val, status);
          if (!replicaFuture.isDone()) {
            replicaFuture.setCompletedFuture(additionalActiveGet);
          }
        }

        @Override
        public void gotData(String k, int flags, byte[] data) {
          assert key.equals(k) : "Wrong key returned";
          val = tcService.decode(tc, new CachedData(flags, data,
            tc.getMaxSize()));
        }

        @Override
        public void complete() {
          latch.countDown();
        }
      });
    additionalActiveGet.setOperation(op);
    mconn.enqueueOperation(key, op);

    if (op.isCancelled()) {
