  private <T> Operation createOperationForReplicaGet(final String key,
    final GetFuture<T> future, final ReplicaGetFuture<T> replicaFuture,
    final CountDownLatch latch, final Transcoder<T> tc, final int replicaIndex,
    final boolean replica) {
    if (replica) {
      return opFact.replicaGet(key, replicaIndex,
        new ReplicaGetOperation.Callback() {
          private Future<T> val = null;

          @Override
          public void receivedStatus(OperationStatus status) {
            future.set(val, status);
            if (!replicaFuture.isDone()) {
              replicaFuture.setCompletedFuture(future);
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
    } else {
      return opFact.get(key, new GetOperation.Callback() {
        private Future<T> val = null;

        @Override
        public void receivedStatus(OperationStatus status) {
          future.set(val, status);
          if (!replicaFuture.isDone()) {
            replicaFuture.setCompletedFuture(future);
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
    }
  }

