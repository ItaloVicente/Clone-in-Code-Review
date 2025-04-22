    if(isDone()) {
      return this;
    }

    synchronized(this) {
      if (!isDone()) {
