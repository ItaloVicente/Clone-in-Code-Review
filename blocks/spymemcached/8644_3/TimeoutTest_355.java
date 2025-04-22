  @Override
  protected void tearDown() throws Exception {
    client.shutdown();
  }

  @Override
  protected void initClient() throws Exception {
    client = new MemcachedClient(new DefaultConnectionFactory() {
      @Override
      public long getOperationTimeout() {
        return 20;
      }
