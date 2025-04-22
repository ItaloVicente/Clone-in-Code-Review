  public synchronized void interruptAllPendingAuth(){
    for (AuthThread toStop : nodeMap.values()) {
      if (toStop.isAlive()) {
        getLogger().warn("Connection shutdown in progress - interrupting "
          + "waiting authentication thread.");
        toStop.interrupt();
      }
    }
  }

