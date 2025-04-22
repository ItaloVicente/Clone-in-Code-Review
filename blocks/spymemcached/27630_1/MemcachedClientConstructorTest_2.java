  public void testEmptyConstructorWithConnect() throws Exception {
    client = new MemcachedClient();
    client.connect(new InetSocketAddress(
            InetAddress.getByName(TestConfig.IPV4_ADDR),
                TestConfig.PORT_NUMBER));
    assertWorking();
  }

