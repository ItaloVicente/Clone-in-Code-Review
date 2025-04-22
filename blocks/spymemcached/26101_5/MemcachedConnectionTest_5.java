  public void testConnectionsStatus() throws Exception {
    ConnectionFactory factory = new DefaultConnectionFactory();
    List<InetSocketAddress> addresses =
      AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":11211");
    Collection<ConnectionObserver> observers =
      new ArrayList<ConnectionObserver>();
    MemcachedConnection mcc = new MemcachedConnection(10240, factory, addresses,
      observers, FailureMode.Retry, new BinaryOperationFactory());
    assertNotNull(mcc.connectionsStatus());
  }

