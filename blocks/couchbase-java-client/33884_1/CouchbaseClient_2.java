  private <T> Operation createOperationForReplicaGets(final String key,
    final OperationFuture<CASValue<T>> future, final ReplicaGetFuture<CASValue<T>> replicaFuture,
    final CountDownLatch latch, final Transcoder<T> tc, final int replicaIndex,
    final boolean replica) {
    if (replica) {
      return opFact.replicaGets(key, replicaIndex,
        new ReplicaGetsOperation.Callback() {
          private CASValue<T> val;
          private boolean usedFuture;

          @Override
          public void receivedStatus(OperationStatus status) {
            future.set(val, status);
            if (!replicaFuture.isDone() && status.isSuccess()) {
              usedFuture = replicaFuture.setCompletedFuture(future);
            }
          }

          @Override
          public void gotData(String key, int flags, long cas, byte[] data) {
            assert key.equals(key) : "Wrong key returned";
            val = new CASValue<T>(cas, tc.decode(new CachedData(flags, data,
              tc.getMaxSize())));
          }

          @Override
          public void complete() {
            latch.countDown();
            if (usedFuture) {
              replicaFuture.signalComplete();
            }
          }
        });
    } else {
      return opFact.gets(key, new GetsOperation.Callback() {
        private CASValue<T> val = null;
        private boolean usedFuture;

        @Override
        public void receivedStatus(OperationStatus status) {
          future.set(val, status);
          if (!replicaFuture.isDone() && status.isSuccess()) {
            usedFuture = replicaFuture.setCompletedFuture(future);
          }
        }

        @Override
        public void gotData(String key, int flags, long cas, byte[] data) {
          assert key.equals(key) : "Wrong key returned";
          val = new CASValue<T>(cas, tc.decode(new CachedData(flags, data,
            tc.getMaxSize())));
        }

        @Override
        public void complete() {
          latch.countDown();
          if (usedFuture) {
            replicaFuture.signalComplete();
          }
        }
      });
    }
  }

