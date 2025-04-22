  private MemcachedNode getNodeForKey(long hash) {
    final MemcachedNode rv;
    if (!ketamaNodes.containsKey(hash)) {
      SortedMap<Long, MemcachedNode> tailMap = ketamaNodes.tailMap(hash);
      if (tailMap.isEmpty()) {
        hash = ketamaNodes.firstKey();
      } else {
        hash = tailMap.firstKey();
      }
