    running = true;
  }

  private void updateMaxTotalRequests() {
    pool.setMaxTotal(viewNodes.size() * pool.getDefaultMaxPerRoute());
  }

  private static HttpHost createHttpHost(final String host, final int port) {
    return new HttpHost(host, port, SCHEME);
  }

  private int getNextNode() {
    return nextNode = ++nextNode % viewNodes.size();
  }

  private boolean hasActiveVBuckets(final HttpHost node) {
    DefaultConfig config = (DefaultConfig) connFactory.getVBucketConfig();
    return config.nodeHasActiveVBuckets(
      new InetSocketAddress(node.getHostName(), node.getPort())
    );
