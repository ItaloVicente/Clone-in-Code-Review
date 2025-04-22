    manager = new ClusterManager(uris, CbTestConfig.CLUSTER_ADMINNAME, CbTestConfig.CLUSTER_PASS);
    try {
      manager.createDefaultBucket(BucketType.COUCHBASE, 100, 0, true);
    }catch (RuntimeException e) {
        assertEquals(e.getMessage(), "Http Error: 401 Reason: Unauthorized Details: No reason given");
    }
    manager = getClusterManager();
