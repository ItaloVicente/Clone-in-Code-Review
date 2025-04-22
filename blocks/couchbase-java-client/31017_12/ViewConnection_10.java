    synchronized (viewNodes) {
      Iterator<HttpHost> iter = viewNodes.iterator();
      while (iter.hasNext()) {
        HttpHost current = iter.next();
        if (!currentViewServers.contains(current)
          || !hasActiveVBuckets(config, current)) {
          iter.remove();
          pool.closeConnectionsForHost(current);
