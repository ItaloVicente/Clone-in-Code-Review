  protected void initClient() throws Exception {
    initClient(new DefaultConnectionFactory() {
      @Override
      public long getOperationTimeout() {
        return 15000;
      }
