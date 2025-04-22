  public void testConnectionsStatus() throws Exception {
    ConnectionFactory factory = new DefaultConnectionFactory();
    List<InetSocketAddress> addresses =
      AddrUtil.getAddresses("127.0.0.1:11211");
    Collection<ConnectionObserver> observers =
      new ArrayList<ConnectionObserver>();
    MemcachedConnection mcc = new MemcachedConnection(10240, factory, addresses,
      observers, FailureMode.Retry, new BinaryOperationFactory());
    System.out.println(mcc.connectionsStatus());
  }

