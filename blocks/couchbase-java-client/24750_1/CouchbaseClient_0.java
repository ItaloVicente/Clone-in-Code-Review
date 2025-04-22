  public Object getFromReplica(String key, ReplicaIndex index) {
    return getFromReplica(key, index, transcoder);
  }

  public <T> T getFromReplica(String key, ReplicaIndex index, Transcoder<T> tc) {
    try {
      return asyncGetFromReplica(key, index, tc).get(operationTimeout,
        TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for value", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Exception waiting for value", e);
    } catch (TimeoutException e) {
      throw new OperationTimeoutException("Timeout waiting for value", e);
    }
  }

  public GetFuture<Object> asyncGetFromReplica(final String key, ReplicaIndex index) {
    return asyncGetFromReplica(key, index, transcoder);
  }

  public <T> GetFuture<T> asyncGetFromReplica(final String key, ReplicaIndex index, final Transcoder<T> tc) {
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
    return rv;
  }

