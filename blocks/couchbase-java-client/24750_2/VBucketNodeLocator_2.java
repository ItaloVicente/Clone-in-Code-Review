  public MemcachedNode getReplica(String key, ReplicaIndex index) {
     TotalConfig totConfig = fullConfig.get();
    Config config = totConfig.getConfig();
    Map<String, MemcachedNode> nodesMap = totConfig.getNodesMap();
    int vbucket = config.getVbucketByKey(key);
    int serverNumber = config.getReplica(vbucket, index.getValue());
    String server = config.getServer(serverNumber);
    MemcachedNode pNode = nodesMap.get(server);
    return pNode;
  }

