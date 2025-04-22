
  public void shutdown() {
    shutdown(-1, TimeUnit.MILLISECONDS);
  }

  public boolean shutdown(long timeout, TimeUnit unit) {
    if (shuttingDown) {
      getLogger().info("Suppressing duplicate attempt to shut down");
      return false;
    }
    cp.shutdown();
    shuttingDown = true;
    String baseName = conn.getName();
    conn.setName(baseName + " - SHUTTING DOWN");
    boolean rv = false;
    try {
      if (timeout > 0) {
        conn.setName(baseName + " - SHUTTING DOWN (waiting)");
        rv = waitForQueues(timeout, unit);
      }
    } finally {
      try {
        conn.setName(baseName + " - SHUTTING DOWN (telling client)");
        conn.shutdown();
        conn.setName(baseName + " - SHUTTING DOWN (informed client)");
        tcService.shutdown();
      } catch (IOException e) {
        getLogger().warn("exception while shutting down", e);
      }
    }
    return rv;
  }
