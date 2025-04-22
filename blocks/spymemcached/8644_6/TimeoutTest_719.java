  public void testGetBulkTimeout() {
    tryTimeout("getbulk", new Runnable() {
      public void run() {
        client.getBulk("k", "k2");
      }
    });
  }
