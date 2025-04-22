  @Override
  public void reconfigure(final Bucket bucket) {
    DefaultConfig config = (DefaultConfig) bucket.getConfig();
    int sizeBeforeReconfigure = viewNodes.size();

    List<HttpHost> currentViewServers = new ArrayList<HttpHost>();
    for (URL server : bucket.getConfig().getCouchServers()) {
      HttpHost host = createHttpHost(server.getHost(), server.getPort());
      currentViewServers.add(host);

      if (!viewNodes.contains(host) && hasActiveVBuckets(config, host)) {
        viewNodes.add(host);
