  public boolean nodeHasActiveVBuckets(InetSocketAddress node) {
    boolean result = serversWithVBuckets.contains(node.getHostName());
    if (!result && node.getAddress() != null) {
      result = serversWithVBuckets.contains(node.getAddress().getHostAddress());
    }

    if (!result) {
      getLogger().debug("Given node " + node + " has no active VBuckets.");
    }
    return result;
  }

  @Override
