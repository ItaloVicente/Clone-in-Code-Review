
    synchronized(this) {
      if (!isDone()) {
        listeners.remove(listener);
      }
    }

