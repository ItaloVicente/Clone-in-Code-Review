  public void connectionEstablished(SocketAddress sa, int reconnectCount) {
    if (authDescriptor != null) {
      if (authDescriptor.authThresholdReached()) {
        this.shutdown();
      } else {
        authMonitor.authConnection(conn, opFact, authDescriptor, findNode(sa));
      }
    }
  }

  private MemcachedNode findNode(SocketAddress sa) {
    MemcachedNode node = null;
    for (MemcachedNode n : conn.getLocator().getAll()) {
      if (n.getSocketAddress().equals(sa)) {
        node = n;
      }
    }
    assert node != null : "Couldn't find node connected to " + sa;
    return node;
  }

  public void connectionLost(SocketAddress sa) {
  }

