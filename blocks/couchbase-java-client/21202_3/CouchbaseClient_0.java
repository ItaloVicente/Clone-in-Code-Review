
  public OperationFuture<Map<String, String>> getKeyStats(String key) {
    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<Map<String, String>> rv =
        new OperationFuture<Map<String, String>>(key, latch, operationTimeout);
    Operation op = opFact.keyStats(key, new StatsOperation.Callback() {
          Map<String, String> stats = new HashMap<String, String>();
          public void gotStat(String name, String val) {
            stats.put(name, val);
          }

          public void receivedStatus(OperationStatus status) {
            rv.set(stats, status);
          }

          public void complete() {
            latch.countDown();
          }
        });
    rv.setOperation(op);
    mconn.enqueueOperation(key, op);
    return rv;
  }
