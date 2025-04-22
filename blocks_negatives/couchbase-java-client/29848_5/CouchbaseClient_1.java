      Operation op = opFact.replicaGet(key, index,
        new ReplicaGetOperation.Callback() {
          private Future<T> val = null;

          @Override
          public void receivedStatus(OperationStatus status) {
            rv.set(val, status);
            if (!replicaFuture.isDone()) {
              replicaFuture.setCompletedFuture(rv);
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
