  public void testConnFactoryWithoutConns() throws Exception {
    try {
      client = new MemcachedClient(new DefaultConnectionFactory() {
        @Override
        public MemcachedConnection createConnection(
            List<InetSocketAddress> addrs) throws IOException {
          return null;
        }
      }, AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":"
              + TestConfig.PORT_NUMBER));
    } catch (AssertionError e) {
      assertEquals("Connection factory failed to make a connection",
          e.getMessage());
    }
  }

