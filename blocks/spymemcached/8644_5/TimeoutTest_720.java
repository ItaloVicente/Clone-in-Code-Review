  public void testIncrTimeout() {
    tryTimeout("incr", new Runnable() {
      public void run() {
        client.incr("k", 1);
      }
    });
  }
