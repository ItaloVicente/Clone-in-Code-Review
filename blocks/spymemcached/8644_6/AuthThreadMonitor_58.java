  public synchronized void authConnection(MemcachedConnection conn,
      OperationFactory opFact, AuthDescriptor authDescriptor,
      MemcachedNode node) {
    interruptOldAuth(node);
    AuthThread newSASLAuthenticator =
        new AuthThread(conn, opFact, authDescriptor, node);
    nodeMap.put(node, newSASLAuthenticator);
  }
