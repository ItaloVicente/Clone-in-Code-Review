  @Override
  public void run() {
    try {
      barrier.await();
      rv = callable.call();
    } catch (Throwable t) {
      throwable = t;
    }
    latch.countDown();
  }
