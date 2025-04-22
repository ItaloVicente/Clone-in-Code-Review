  protected void queueReconnect(MemcachedNode qa) {
    if (!shutDown) {
      getLogger().warn("Closing, and reopening %s, attempt %d.", qa,
          qa.getReconnectCount());
      if (qa.getSk() != null) {
        qa.getSk().cancel();
        assert !qa.getSk().isValid() : "Cancelled selection key is valid";
      }
      qa.reconnecting();
      try {
        if (qa.getChannel() != null && qa.getChannel().socket() != null) {
          qa.getChannel().socket().close();
        } else {
          getLogger().info("The channel or socket was null for %s", qa);
        }
      } catch (IOException e) {
        getLogger().warn("IOException trying to close a socket", e);
      }
      qa.setChannel(null);
