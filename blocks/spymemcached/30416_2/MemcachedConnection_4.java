  protected void queueReconnect(final MemcachedNode node) {
    if (shutDown) {
      return;
    }
    getLogger().warn("Closing, and reopening %s, attempt %d.", node,
      node.getReconnectCount());
