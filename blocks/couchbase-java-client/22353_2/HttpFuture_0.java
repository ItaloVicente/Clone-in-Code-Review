    waitForAndCheckOperation(duration, units);
    return objRef.get();
  }

  protected void waitForAndCheckOperation(long duration, TimeUnit units)
    throws InterruptedException, TimeoutException, ExecutionException {
