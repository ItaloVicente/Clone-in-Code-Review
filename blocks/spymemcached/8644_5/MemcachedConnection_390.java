    return sb.toString();
  }

  private void queueReconnect(MemcachedNode qa) {
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

      long delay = (long) Math.min(maxDelay, Math.pow(2,
          qa.getReconnectCount())) * 1000;
      long reconTime = System.currentTimeMillis() + delay;

      while (reconnectQueue.containsKey(reconTime)) {
        reconTime++;
      }

      reconnectQueue.put(reconTime, qa);

      qa.setupResend();

      if (failureMode == FailureMode.Redistribute) {
        redistributeOperations(qa.destroyInputQueue());
      } else if (failureMode == FailureMode.Cancel) {
        cancelOperations(qa.destroyInputQueue());
      }
    }
  }
