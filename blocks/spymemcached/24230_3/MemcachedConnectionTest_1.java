
  public void testBelongsToCluster() throws Exception {
    ConnectionFactory factory = new DefaultConnectionFactory();
    Collection<ConnectionObserver> observers =
      new ArrayList<ConnectionObserver>();
    OperationFactory opfactory = new BinaryOperationFactory();

    MemcachedNode node = new MockMemcachedNode(
      new InetSocketAddress(TestConfig.IPV4_ADDR, TestConfig.PORT_NUMBER));
    MemcachedNode node2 = new MockMemcachedNode(
      new InetSocketAddress("invalidIpAddr", TestConfig.PORT_NUMBER));

    List<InetSocketAddress> nodes = new ArrayList<InetSocketAddress>();
    nodes.add((InetSocketAddress)node.getSocketAddress());

    MemcachedConnection conn = new MemcachedConnection(
      100, factory, nodes, observers, FailureMode.Retry, opfactory);
    assertTrue(conn.belongsToCluster(node));
    assertFalse(conn.belongsToCluster(node2));
  }
