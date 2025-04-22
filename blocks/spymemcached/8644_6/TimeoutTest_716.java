  public void testCasTimeout() {
    tryTimeout("cas", new Runnable() {
      public void run() {
        client.cas("k", 1, "blah");
      }
    });
  }
