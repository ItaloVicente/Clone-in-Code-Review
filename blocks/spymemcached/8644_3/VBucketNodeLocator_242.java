  }

  public int getVBucketIndex(String key) {
    Config config = fullConfig.get().getConfig();
    return config.getVbucketByKey(key);
  }

  private Map<String, MemcachedNode> fillNodesEntries(
      Collection<MemcachedNode> nodes) {
    HashMap<String, MemcachedNode> vbnodesMap =
        new HashMap<String, MemcachedNode>();
    getLogger().debug("Updating nodesMap in VBucketNodeLocator.");
    for (MemcachedNode node : nodes) {
      InetSocketAddress addr = (InetSocketAddress) node.getSocketAddress();
      String address = addr.getAddress().getHostName() + ":" + addr.getPort();
      String hostname =
          addr.getAddress().getHostAddress() + ":" + addr.getPort();
      getLogger().debug("Adding node with hostname %s and address %s.",
          hostname, address);
      getLogger().debug("Node added is %s.", node);
      vbnodesMap.put(address, node);
      vbnodesMap.put(hostname, node);
