  /**
   * This test is performed by having the client connect to a host
   * which is up, but using a bad port (i.e. not the default 8091)
   *
   * @pre  First the running client is shut down and a new instance
   * is created using URIs of invalid addresses. Next an attempt is
   * made to create the default bucket for this new instance.
   * @post  The connection should not succeed, after which the
   * previous cluster manager instance is restored.
   * @throws InterruptedException the interrupted exception
   */
  public void testConnectionRefused() throws InterruptedException {
    List<URI> uris = new LinkedList<URI>();
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

  /**
   * This test is performed by having the client connect
   * to an IP for which no valid host is assigned.
   *
   * @pre  First the running client is shut down and a new instance
   * is created using URIs of invalid addresses. Next an attempt is
   * made to create the default bucket for this new instance.
   * @post  The connection should not succeed, after which the
   * previous cluster manager instance is restored.
   * @throws InterruptedException the interrupted exception
   */
  public void testNetworkUnreachable() throws InterruptedException {
    List<URI> uris = new LinkedList<URI>();
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
