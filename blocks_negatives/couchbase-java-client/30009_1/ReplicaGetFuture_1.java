  public boolean allDone() {
     boolean allDone = true;
    for(GetFuture future : monitoredFutures) {
      if(!future.isDone()) {
        allDone = false;
      }
    }
    return allDone;
  }

