  public Object getFromReplica(String key) {
    return getFromReplica(key, transcoder);
  }

  public <T> T getFromReplica(String key, Transcoder<T> tc) {
    try {
      return asyncGetFromReplica(key, tc).get(operationTimeout,
        TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for value", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Exception waiting for value", e);
    } catch (TimeoutException e) {
      throw new OperationTimeoutException("Timeout waiting for value", e);
    }
  }

  public ReplicaGetFuture<Object> asyncGetFromReplica(final String key) {
    return asyncGetFromReplica(key, transcoder);
  }

  public <T> ReplicaGetFuture<T> asyncGetFromReplica(final String key, final Transcoder<T> tc) {
    int replicaCount = cbConnFactory.getVBucketConfig().getReplicasCount();
    if(replicaCount == 0) {
      throw new RuntimeException("Currently, there is no replica available for"
        + " the given key (\"" + key + "\").");
    }

    List<GetFuture<T>> futures = new ArrayList<GetFuture<T>>();
    for(int index=0; index < replicaCount; index++) {
      final CountDownLatch latch = new CountDownLatch(1);
      final GetFuture<T> rv = new GetFuture<T>(latch, operationTimeout, key);
      Operation op = opFact.replicaGet(key, index, new ReplicaGetOperation.Callback() {
        private Future<T> val = null;

        public void receivedStatus(OperationStatus status) {
          rv.set(val, status);
        }

        public void gotData(String k, int flags, byte[] data) {
          assert key.equals(k) : "Wrong key returned";
          val =
              tcService.decode(tc, new CachedData(flags, data, tc.getMaxSize()));
        }

        public void complete() {
          latch.countDown();
        }
      });
      rv.setOperation(op);
      mconn.enqueueOperation(key, op);
      futures.add(rv);
    }

    return new ReplicaGetFuture<T>(operationTimeout, futures);
  }

