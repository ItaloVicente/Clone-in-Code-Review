  public void testCreateFromBuilder() throws Exception {
    ClusterManager manager = new ClusterManagerBuilder()
      .setTcpNoDelay(false)
      .setIoThreadCount(10)
      .build(
        Arrays.asList(URI.create("http://" + TestConfig.IPV4_ADDR
          + ":8091/pools")),
        CbTestConfig.CLUSTER_ADMINNAME,
        CbTestConfig.CLUSTER_PASS
      );

    manager.createNamedBucket(BucketType.COUCHBASE, "custom", 256, 0, "foo",
      false);
  }

