  @Override
  protected void tearDown() throws Exception {
    client.shutdown();
    client = null;
    initClient();
    flushPause();
    assertTrue(client.flush().get());
    client.shutdown();
    client = null;
    super.tearDown();
  }
