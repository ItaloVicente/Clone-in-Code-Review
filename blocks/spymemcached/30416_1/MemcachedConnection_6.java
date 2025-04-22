  private void potentiallyCloseLeakingChannel(final SocketChannel ch,
    final MemcachedNode node) {
    if (ch != null && !ch.isConnected() && !ch.isConnectionPending()) {
      try {
        ch.close();
      } catch (IOException e) {
        getLogger().error("Exception closing channel: %s", node, e);
      }
    }
  }

