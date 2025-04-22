  @Override
  protected void tearDown() throws Exception {
    ts.shutdown();
    assertTrue(ts.isShutdown());
    super.tearDown();
  }
