
  @Ignore
  @Override
  public void testDelayedFlush() throws Exception {
  }

  @Ignore
  @Override
  public void testFlush() throws Exception {
  }

  @Override
  protected void tearDown() throws Exception {

    client.shutdown(200, TimeUnit.MILLISECONDS);
    client = null;
    System.gc();
  }
