  public void testConnectionRefused() throws InterruptedException {
    List<URI> uris = new LinkedList<URI>();
    uris.add(URI.create("http://" + TestConfig.IPV4_ADDR + ":3454/pools"));
    manager.shutdown();
    manager = new ClusterManager(uris, CbTestConfig.CLUSTER_ADMINNAME,
      CbTestConfig.CLUSTER_PASS);
    String message = "";
    try {
      manager.createDefaultBucket(BucketType.COUCHBASE, 100, 0, true);
    } catch (RuntimeException e) {
      message = e.getMessage();
    }
    assertEquals("Unable to connect to cluster", message);
    manager = getClusterManager();
  }

  public void testNetworkUnreachable() throws InterruptedException {
    List<URI> uris = new LinkedList<URI>();
    uris.add(URI.create("http://123.123.123.123:8091/pools"));
    manager.shutdown();
    manager = new ClusterManager(uris, CbTestConfig.CLUSTER_ADMINNAME,
      CbTestConfig.CLUSTER_PASS);
    String message = "";
    try {
      manager.createDefaultBucket(BucketType.COUCHBASE, 100, 0, true);
    } catch (RuntimeException e) {
      message = e.getMessage();
    }
    assertEquals("Unable to connect to cluster", message);
    manager = getClusterManager();
  }
