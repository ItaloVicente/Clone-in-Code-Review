  public SyncThread(CyclicBarrier b, Callable<T> c) {
    super("SyncThread");
    setDaemon(true);
    callable = c;
    barrier = b;
    latch = new CountDownLatch(1);
    start();
  }
