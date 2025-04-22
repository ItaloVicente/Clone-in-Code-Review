  public <T> OperationFuture<Boolean> asyncUnlock(final String key,
          long casId, final Transcoder<T> tc) {
    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<Boolean> rv = new OperationFuture<Boolean>(key,
            latch, operationTimeout);
    Operation op = opFact.unlock(key, casId, new OperationCallback() {

      @Override
      public void receivedStatus(OperationStatus s) {
        rv.set(s.isSuccess(), s);
      }

      @Override
      public void complete() {
        latch.countDown();
      }
    });
    rv.setOperation(op);
    mconn.enqueueOperation(key, op);
    return rv;
  }

  public OperationFuture<Boolean> asyncUnlock(final String key,
          long casId) {
    return asyncUnlock(key, casId, transcoder);
  }

  public <T> Boolean unlock(final String key,
          long casId, final Transcoder<T> tc) {
    try {
      return asyncUnlock(key, casId, tc).get(operationTimeout,
          TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for value", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Exception waiting for value", e);
    } catch (TimeoutException e) {
      throw new OperationTimeoutException("Timeout waiting for value", e);
    }

  }

  public Boolean unlock(final String key,
          long casId) {
    return unlock(key, casId, transcoder);
  }

