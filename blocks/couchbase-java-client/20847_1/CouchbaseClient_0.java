  public Map<MemcachedNode, ObserveResponse> observe(final String key,
      final long cas) {
    Config cfg = ((CouchbaseConnectionFactory) connFactory).getVBucketConfig();
    VBucketNodeLocator locator = ((VBucketNodeLocator)
        ((CouchbaseConnection) mconn).getLocator());

    final int vb = locator.getVBucketIndex(key);
    List<MemcachedNode> bcastNodes = new ArrayList<MemcachedNode>();

    bcastNodes.add(locator.getServerByIndex(cfg.getMaster(vb)));
    for (int i = 1; i <= cfg.getReplicasCount(); i++) {
      bcastNodes.add(locator.getServerByIndex(cfg.getReplica(vb, i-1)));
