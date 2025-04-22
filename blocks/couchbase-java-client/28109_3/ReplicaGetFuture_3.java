    this.monitoredFutures = new ArrayList<GetFuture<T>>();
  }

  public void addFutureToMonitor(GetFuture<T> future) {
    this.monitoredFutures.add(future);
  }

  public void setCompletedFuture(GetFuture<T> future) {
    notifyListeners();
    this.completedFuture = future;
