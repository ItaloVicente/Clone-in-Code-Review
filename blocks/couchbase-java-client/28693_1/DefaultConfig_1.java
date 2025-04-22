  public boolean nodeHasActiveVBuckets(String node) {
    boolean result = serversWithVBuckets.contains(node);
    if (!result) {
      getLogger().debug("Given node " + node + " has no active VBuckets.");
    }
    return result;
  }

  @Override
