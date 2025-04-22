
  private static boolean hasActiveVBuckets(final DefaultConfig config,
    final HttpHost node) {
    return config.nodeHasActiveVBuckets(
      new InetSocketAddress(node.getHostName(), node.getPort())
    );
  }

  List<HttpHost> getConnectedHosts() {
    return viewNodes;
  }

