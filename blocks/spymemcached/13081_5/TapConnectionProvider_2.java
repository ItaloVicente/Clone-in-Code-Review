  public void addTapAckOp(MemcachedNode node, final Operation op) {
    conn.addOperation(node, op);
  }

  public CountDownLatch broadcastOp(final BroadcastOpFactory of) {
    if (shuttingDown) {
      throw new IllegalStateException("Shutting down");
    }
    return conn.broadcastOperation(of, conn.getLocator().getAll());
