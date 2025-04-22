      return this;
    }

    synchronized(this) {
      if (!isDone()) {
        listeners.add(listener);
        return this;
      }
