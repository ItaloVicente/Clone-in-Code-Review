  @Test
  public void shouldDetectChangeOnOnlyReplicaVBucket() throws Exception {
    List<String> servers = Arrays.asList("node1", "node2");
    List<URL> couchServers = Arrays.asList(new URL("http://node1:8092/"),
      new URL("http://node2:8092/"));
    List<String> endpoints = Arrays.asList("http://node1:8091/pools",
      "http://node2:8091/pools");

    final int numVBuckets = 32;
    List<VBucket> vbucketsOld = new ArrayList<VBucket>();
    for (int i = 0; i < numVBuckets; i++) {
      vbucketsOld.add(new VBucket((short)(i % 2)));
    }
    List<VBucket> vbucketsNew = new ArrayList<VBucket>();
    for (int i = 0; i < numVBuckets; i++) {
      vbucketsNew.add(new VBucket((short)(i % 2), (short) 1));
    }

    CouchbaseConfig oldConfig = new CouchbaseConfig(
      hashAlgorithm, 2, 1, numVBuckets, servers, vbucketsOld, couchServers,
      endpoints, false);
    CouchbaseConfig newConfig = new CouchbaseConfig(
      hashAlgorithm, 2, 1, numVBuckets, servers, vbucketsNew, couchServers,
      endpoints, false);

    ConfigDifference difference = oldConfig.compareTo(newConfig);
    assertTrue(difference.getVbucketsChanges() > 0);
  }

