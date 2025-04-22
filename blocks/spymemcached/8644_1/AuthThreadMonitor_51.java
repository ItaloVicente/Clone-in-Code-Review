  private Map<Object, AuthThread> nodeMap;

  public AuthThreadMonitor() {
    nodeMap = new HashMap<Object, AuthThread>();
  }

  public synchronized void authConnection(MemcachedConnection conn,
      OperationFactory opFact, AuthDescriptor authDescriptor,
      MemcachedNode node) {
    interruptOldAuth(node);
    AuthThread newSASLAuthenticator =
        new AuthThread(conn, opFact, authDescriptor, node);
    nodeMap.put(node, newSASLAuthenticator);
  }

  private void interruptOldAuth(MemcachedNode nodeToStop) {
    AuthThread toStop = nodeMap.get(nodeToStop);
    if (toStop != null) {
      if (toStop.isAlive()) {
        getLogger().warn(
            "Incomplete authentication interrupted for node " + nodeToStop);
        toStop.interrupt();
      }

      nodeMap.remove(nodeToStop);
    }
  }
