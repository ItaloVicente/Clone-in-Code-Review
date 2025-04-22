    List<HttpHost> currentViewServers = new ArrayList<HttpHost>();
    for (URL server : bucket.getConfig().getCouchServers()) {
      HttpHost host = createHttpHost(server.getHost(), server.getPort());
      currentViewServers.add(host);

      if (!viewNodes.contains(host) && hasActiveVBuckets(host)) {
        viewNodes.add(host);
