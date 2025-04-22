  public MemcachedNode getServerByIndex(int k) {
    TotalConfig totConfig = fullConfig.get();
    Config config = totConfig.getConfig();
    Map<String, MemcachedNode> nodesMap = totConfig.getNodesMap();

    String server = config.getServer(k);
    return nodesMap.get(server);
  }
