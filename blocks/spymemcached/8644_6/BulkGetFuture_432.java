  public Map<String, T> getSome(long to, TimeUnit unit)
    throws InterruptedException, ExecutionException {
    Collection<Operation> timedoutOps = new HashSet<Operation>();
    Map<String, T> ret = internalGet(to, unit, timedoutOps);
    if (timedoutOps.size() > 0) {
      timeout = true;
      LoggerFactory.getLogger(getClass()).warn(
          new CheckedOperationTimeoutException("Operation timed out: ",
              timedoutOps).getMessage());
    }
    return ret;

  }

  public Map<String, T> get(long to, TimeUnit unit)
    throws InterruptedException, ExecutionException, TimeoutException {
    Collection<Operation> timedoutOps = new HashSet<Operation>();
    Map<String, T> ret = internalGet(to, unit, timedoutOps);
    if (timedoutOps.size() > 0) {
      this.timeout = true;
      status = new OperationStatus(false, "Timed out");
      throw new CheckedOperationTimeoutException("Operation timed out.",
          timedoutOps);
    }
    return ret;
  }

  private Map<String, T> internalGet(long to, TimeUnit unit,
      Collection<Operation> timedoutOps) throws InterruptedException,
      ExecutionException {
    if (!latch.await(to, unit)) {
      for (Operation op : ops) {
        if (op.getState() != OperationState.COMPLETE) {
          MemcachedConnection.opTimedOut(op);
          timedoutOps.add(op);
        } else {
          MemcachedConnection.opSucceeded(op);
