  private static final long TAP_DUMP_TIMEOUT = 2000;

  @Override
  protected void initClient() throws Exception {
    initClient(new BinaryConnectionFactory() {
      @Override
      public long getOperationTimeout() {
        return 15000;
      }
