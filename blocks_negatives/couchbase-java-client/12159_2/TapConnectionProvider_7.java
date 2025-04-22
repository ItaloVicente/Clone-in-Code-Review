  public boolean addObserver(ConnectionObserver obs) {
    boolean rv = conn.addObserver(obs);
    if (rv) {
      for (MemcachedNode node : conn.getLocator().getAll()) {
        if (node.isActive()) {
          obs.connectionEstablished(node.getSocketAddress(), -1);
        }
      }
    }
    return rv;
