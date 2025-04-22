  public MemcachedNode getReplica(String key, ReplicaIndex index) {
     TotalConfig totConfig = fullConfig.get();
    Config config = totConfig.getConfig();
    Map<String, MemcachedNode> nodesMap = totConfig.getNodesMap();
    int vbucket = config.getVbucketByKey(key);
    int serverNumber = config.getReplica(vbucket, index.getValue());

    if(serverNumber == -1) {
      throw new RuntimeException("The key " + key + " pointed to vbucket "
        + vbucket + ", for which no server is responsible in the cluster map."
        + "This can be an indication that either no replica is defined for a "
        + "failed server or more nodes have been failed over than replicas "
        + "defined.");
    }

    String server = config.getServer(serverNumber);
    MemcachedNode pNode = nodesMap.get(server);
    return pNode;
  }

