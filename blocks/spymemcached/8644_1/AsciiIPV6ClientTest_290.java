  @Override
  protected void initClient(ConnectionFactory cf) throws Exception {
    client =
        new MemcachedClient(cf, AddrUtil.getAddresses(TestConfig.IPV6_ADDR
            + ":11211"));
  }
