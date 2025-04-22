  public void testGetTimeout() {
    tryTimeout("get", new Runnable() {
      public void run() {
        client.get("k");
      }
    });
  }
