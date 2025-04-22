    return Collections.unmodifiableMap(vbnodesMap);
  }

  public MemcachedNode getAlternative(String k,
      Collection<MemcachedNode> notMyVbucketNodes) {
    Map<String, MemcachedNode> nodesMap =
        new HashMap<String, MemcachedNode>(fullConfig.get().getNodesMap());
    Collection<MemcachedNode> nodes = nodesMap.values();
    nodes.removeAll(notMyVbucketNodes);
    if (nodes.isEmpty()) {
      return null;
    } else {
      return nodes.iterator().next();
