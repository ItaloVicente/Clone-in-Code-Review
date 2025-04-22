    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for value", e);
    }


    blatch = broadcastOp(new BroadcastOpFactory() {

      public Operation newOp(final MemcachedNode n,
              final CountDownLatch latch) {
        return opFact.observe(key, cas, vb, new ObserveOperation.Callback() {

          public void receivedStatus(OperationStatus s) {
          }

          public void gotData(String key, long retCas,
                  ObserveResponse or) {
            for (int i = 1; i <= replicas; i++) {
              if (ora[i] == ObserveResponse.UNINITIALIZED) {
                ora[i] = or;
                if (((or == ObserveResponse.FOUND_PERSISTED)
                        || (or == ObserveResponse.FOUND_NOT_PERSISTED))
                        && cas != 0
                        && retCas != cas) {
                  ora[i] = ObserveResponse.MODIFIED;
                }
                break;
              }
            }
          }

          public void complete() {
            latch.countDown();
          }
        });
      }
    }, replicaList);
    try {
      blatch.await(operationTimeout, TimeUnit.MILLISECONDS);
      return ora;
