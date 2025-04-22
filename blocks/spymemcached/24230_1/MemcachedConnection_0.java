  private boolean belongsToCluster(MemcachedNode node) {
    for (MemcachedNode n : locator.getAll()) {
      if (n.getSocketAddress().equals(node.getSocketAddress())) {
        return true;
      }
    }
    return false;
  }

