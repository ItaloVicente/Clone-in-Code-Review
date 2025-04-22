  public void testConnectionRefused() throws InterruptedException {
    List<URI> uris = new LinkedList<URI>();
    uris.add(URI.create("http://" + TestConfig.IPV4_ADDR + ":8080/pools"));
    manager.shutdown();
    manager = new ClusterManager(uris, CbTestConfig.CLUSTER_ADMINNAME,
      CbTestConfig.CLUSTER_PASS);
    try {
      manager.createDefaultBucket(BucketType.COUCHBASE, 100, 0, true);
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "Unable to connect to cluster");
    }
    manager = getClusterManager();
  }

  public void testNetworkUnreachable() throws InterruptedException {
    List<URI> uris = new LinkedList<URI>();
    uris.add(URI.create("http://10.0.0.41:8091/pools"));
    manager.shutdown();
    manager = new ClusterManager(uris, CbTestConfig.CLUSTER_ADMINNAME,
      CbTestConfig.CLUSTER_PASS);
    try {
      manager.createDefaultBucket(BucketType.COUCHBASE, 100, 0, true);
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "Unable to connect to cluster");
    }
    manager = getClusterManager();
  }
