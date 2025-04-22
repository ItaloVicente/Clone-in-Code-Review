  public void testGetsTimeout() {
    tryTimeout("gets", new Runnable() {
      public void run() {
        client.gets("k");
      }
    });
  }
