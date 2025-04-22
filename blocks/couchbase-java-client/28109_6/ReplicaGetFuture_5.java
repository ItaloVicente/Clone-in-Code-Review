  @Override
  public ReplicaGetFuture<T> addListener(
    ReplicaGetCompletionListener listener) {
    super.addToListeners((GenericCompletionListener) listener);
    return this;
  }

  @Override
  public ReplicaGetFuture<T> removeListener(
    ReplicaGetCompletionListener listener) {
    super.removeFromListeners((GenericCompletionListener) listener);
    return this;
  }


