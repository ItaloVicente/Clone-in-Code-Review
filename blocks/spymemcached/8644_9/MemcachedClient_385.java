    return rv.keySet();
  }

  public void shutdown() {
    shutdown(-1, TimeUnit.MILLISECONDS);
  }

  public boolean shutdown(long timeout, TimeUnit unit) {
    if (shuttingDown) {
      getLogger().info("Suppressing duplicate attempt to shut down");
      return false;
    }
    shuttingDown = true;
    String baseName = mconn.getName();
    mconn.setName(baseName + " - SHUTTING DOWN");
    boolean rv = false;
    try {
      if (timeout > 0) {
        mconn.setName(baseName + " - SHUTTING DOWN (waiting)");
        rv = waitForQueues(timeout, unit);
      }
    } finally {
      try {
        mconn.setName(baseName + " - SHUTTING DOWN (telling client)");
        mconn.shutdown();
        mconn.setName(baseName + " - SHUTTING DOWN (informed client)");
        tcService.shutdown();
      } catch (IOException e) {
        getLogger().warn("exception while shutting down", e);
      }
    }
    return rv;
  }

  public boolean waitForQueues(long timeout, TimeUnit unit) {
    CountDownLatch blatch = broadcastOp(new BroadcastOpFactory() {
      public Operation newOp(final MemcachedNode n,
          final CountDownLatch latch) {
        return opFact.noop(new OperationCallback() {
          public void complete() {
            latch.countDown();
          }

          public void receivedStatus(OperationStatus s) {
          }
        });
      }
    }, mconn.getLocator().getAll(), false);
    try {
      return blatch.await(timeout, unit);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for queues", e);
    }
  }

  public boolean addObserver(ConnectionObserver obs) {
    boolean rv = mconn.addObserver(obs);
    if (rv) {
      for (MemcachedNode node : mconn.getLocator().getAll()) {
        if (node.isActive()) {
          obs.connectionEstablished(node.getSocketAddress(), -1);
        }
      }
    }
    return rv;
  }

  public boolean removeObserver(ConnectionObserver obs) {
    return mconn.removeObserver(obs);
  }

  public void connectionEstablished(SocketAddress sa, int reconnectCount) {
    if (authDescriptor != null) {
      if (authDescriptor.authThresholdReached()) {
        this.shutdown();
      }
      authMonitor.authConnection(mconn, opFact, authDescriptor, findNode(sa));
    }
  }

  private MemcachedNode findNode(SocketAddress sa) {
    MemcachedNode node = null;
    for (MemcachedNode n : mconn.getLocator().getAll()) {
      if (n.getSocketAddress().equals(sa)) {
        node = n;
      }
    }
    assert node != null : "Couldn't find node connected to " + sa;
    return node;
  }
