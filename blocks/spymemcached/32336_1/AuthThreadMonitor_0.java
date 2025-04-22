    for (AuthThread toStop : nodeMap.values()) {
      if (toStop.isAlive()) {
        getLogger().warn(
            "Connection shutdown in progress - interrupting incomplete authentication" + toStop);
        toStop.interrupt();
      }
    }
