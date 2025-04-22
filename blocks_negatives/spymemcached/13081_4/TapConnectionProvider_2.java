  CountDownLatch broadcastOp(final BroadcastOpFactory of) {
    return broadcastOp(of, conn.getLocator().getAll(), true);
  }

  CountDownLatch broadcastOp(final BroadcastOpFactory of,
      Collection<MemcachedNode> nodes) {
    return broadcastOp(of, nodes, true);
  }

