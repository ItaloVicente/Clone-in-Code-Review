  private final boolean tainted;

  public CouchbaseConfig(HashAlgorithm hashAlgorithm, int serversCount,
    int replicasCount, int vbucketsCount, List<String> servers,
    List<VBucket> vbuckets, List<URL> couchServers,
    List<String> restEndpoints, boolean hasForwardMap) {
