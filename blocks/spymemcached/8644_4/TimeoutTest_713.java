  public void testIncrWithDefTimeout() {
    tryTimeout("incrWithDef", new Runnable() {
      public void run() {
        client.incr("k", 1, 5);
      }
    });
  }
