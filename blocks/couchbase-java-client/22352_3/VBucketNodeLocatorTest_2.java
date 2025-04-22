
  @Test
  public void testNoMasterServerForVbucket() {
    MemcachedNodeMockImpl node1 = new MemcachedNodeMockImpl();
    MemcachedNodeMockImpl node2 = new MemcachedNodeMockImpl();
    node1.setSocketAddress(new InetSocketAddress("127.0.0.1", 11211));
    node2.setSocketAddress(new InetSocketAddress("127.0.0.1", 11210));

    ConfigFactory configFactory = new DefaultConfigFactory();
    Config config = configFactory.create(NO_REPLICA_CONFIG_IN_ENVELOPE);

    VBucketNodeLocator locator = new VBucketNodeLocator(
      Arrays.asList((MemcachedNode) node1, node2),
      config);

    boolean success = false;
    try {
      MemcachedNode primary = locator.getPrimary("key1");
    } catch(RuntimeException e) {
      success = true;
    }
    assertTrue("No RuntimeException thrown when vbucket master is -1", success);
  }
