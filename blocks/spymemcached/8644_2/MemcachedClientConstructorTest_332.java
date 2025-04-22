  private MemcachedClient client = null;

  @Override
  protected void tearDown() throws Exception {
    if (client != null) {
      try {
        client.shutdown();
      } catch (NullPointerException e) {

      }
    }
    super.tearDown();
  }

  private void assertWorking() throws Exception {
    Map<SocketAddress, String> versions = client.getVersions();
    assertEquals("/" + TestConfig.IPV4_ADDR + ":11211", versions.keySet()
        .iterator().next().toString());
  }

  private void assertArgRequired(IllegalArgumentException e) {
    assertEquals("You must have at least one server to connect to",
        e.getMessage());
  }

  public void testVarargConstructor() throws Exception {
    client =
        new MemcachedClient(new InetSocketAddress(
            InetAddress.getByName(TestConfig.IPV4_ADDR), 11211));
    assertWorking();
  }

  public void testEmptyVarargConstructor() throws Exception {
    try {
      client = new MemcachedClient();
      fail("Expected illegal arg exception, got " + client);
    } catch (IllegalArgumentException e) {
      assertArgRequired(e);
    }
  }

  public void testNulListConstructor() throws Exception {
    try {
      List<InetSocketAddress> l = null;
      client = new MemcachedClient(l);
      fail("Expected null pointer exception, got " + client);
    } catch (NullPointerException e) {
      assertEquals("Server list required", e.getMessage());
    }
  }

  public void testEmptyListConstructor() throws Exception {
    try {
      client = new MemcachedClient(Collections.<InetSocketAddress>emptyList());
      fail("Expected illegal arg exception, got " + client);
    } catch (IllegalArgumentException e) {
      assertArgRequired(e);
    }
  }

  public void testNullFactoryConstructor() throws Exception {
    try {
      client =
          new MemcachedClient(null, AddrUtil.getAddresses(TestConfig.IPV4_ADDR
              + ":11211"));
      fail("Expected null pointer exception, got " + client);
    } catch (NullPointerException e) {
      assertEquals("Connection factory required", e.getMessage());
    }
  }

  public void testNegativeTimeout() throws Exception {
    try {
      client = new MemcachedClient(new DefaultConnectionFactory() {
        @Override
        public long getOperationTimeout() {
          return -1;
        }
      }, AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":11211"));
      fail("Expected null pointer exception, got " + client);
    } catch (IllegalArgumentException e) {
      assertEquals("Operation timeout must be positive.", e.getMessage());
    }
  }

  public void testZeroTimeout() throws Exception {
    try {
      client = new MemcachedClient(new DefaultConnectionFactory() {
        @Override
        public long getOperationTimeout() {
          return 0;
        }
      }, AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":11211"));
      fail("Expected null pointer exception, got " + client);
    } catch (IllegalArgumentException e) {
      assertEquals("Operation timeout must be positive.", e.getMessage());
    }
  }

  public void testConnFactoryWithoutOpFactory() throws Exception {
    try {
      client = new MemcachedClient(new DefaultConnectionFactory() {
        @Override
        public OperationFactory getOperationFactory() {
          return null;
        }
      }, AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":11211"));
    } catch (AssertionError e) {
      assertEquals("Connection factory failed to make op factory",
          e.getMessage());
    }
  }

  public void testConnFactoryWithoutConns() throws Exception {
    try {
      client = new MemcachedClient(new DefaultConnectionFactory() {
        @Override
        public MemcachedConnection createConnection(
            List<InetSocketAddress> addrs) throws IOException {
          return null;
        }
      }, AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":11211"));
    } catch (AssertionError e) {
      assertEquals("Connection factory failed to make a connection",
          e.getMessage());
    }

  }

  public void testArraymodNodeLocatorAccessor() throws Exception {
    client =
        new MemcachedClient(AddrUtil.getAddresses(TestConfig.IPV4_ADDR
            + ":11211"));
    assertTrue(client.getNodeLocator() instanceof ArrayModNodeLocator);
    assertTrue(client.getNodeLocator().getPrimary("x")
        instanceof MemcachedNodeROImpl);
  }

  public void testKetamaNodeLocatorAccessor() throws Exception {
    client =
        new MemcachedClient(new KetamaConnectionFactory(),
            AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":11211"));
    assertTrue(client.getNodeLocator() instanceof KetamaNodeLocator);
    assertTrue(client.getNodeLocator().getPrimary("x")
        instanceof MemcachedNodeROImpl);
  }
