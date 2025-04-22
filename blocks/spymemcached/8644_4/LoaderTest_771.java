  public void init() throws Exception {
    client =
        new MemcachedClient(new ConnectionFactoryBuilder()
            .setProtocol(Protocol.BINARY).setOpQueueMaxBlockTime(1000).build(),
            AddrUtil.getAddresses("localhost:11211"));
  }
