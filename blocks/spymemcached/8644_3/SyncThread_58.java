  public T getResult() throws Throwable {
    latch.await();
    if (throwable != null) {
      throw throwable;
    }
    return rv;
  }
