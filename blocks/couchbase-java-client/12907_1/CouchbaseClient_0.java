  public OperationFuture<Boolean> unlock(final String key, long casId) {
    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<Boolean> rv = new OperationFuture<Boolean>(key,
            latch, operationTimeout);
    Operation op = opFact.unlock(key, casId,
            new UnlockOperation.Callback() {
      private CASValue val = null;

      public void receivedStatus(OperationStatus s) {
        rv.set(s.isSuccess(), s);
      }

      public void gotData(String k, int flags, long cas, byte[] data) {
        assert key.equals(k) : "Wrong key returned";
        assert cas > 0 : "CAS was less than zero:  " + cas;
      }

      public void complete() {
        latch.countDown();
      }
    });
    rv.setOperation(op);
    mconn.enqueueOperation(key, op);
    return rv;
  }
