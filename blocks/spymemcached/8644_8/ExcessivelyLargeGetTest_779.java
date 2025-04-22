  public ExcessivelyLargeGetTest() throws Exception {
    client = new MemcachedClient(new ConnectionFactoryBuilder()
        .setProtocol(Protocol.BINARY).setOpTimeout(15000).build(),
        AddrUtil.getAddresses("127.0.0.1:11211"));
    keys = new ArrayList<String>(N);
    new Random().nextBytes(value);
  }
