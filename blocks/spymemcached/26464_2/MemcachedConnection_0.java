    boolean stillCheckingTimeouts = true;
    while(stillCheckingTimeouts) {
      try {
        for (SelectionKey sk : selector.keys()) {
          MemcachedNode mn = (MemcachedNode) sk.attachment();
          if (mn.getContinuousTimeout() > timeoutExceptionThreshold) {
            getLogger().warn("%s exceeded continuous timeout threshold", sk);
            lostConnection(mn);
          }
        }
        stillCheckingTimeouts = false;
      } catch(ConcurrentModificationException e) {
        getLogger().warn("Retrying selector keys after "
          + "ConcurrentModificationException caught", e);
        continue;
