    return completedFuture != null && completedFuture.isDone();
  }

  public boolean allDone() {
     boolean allDone = true;
    for(GetFuture future : monitoredFutures) {
