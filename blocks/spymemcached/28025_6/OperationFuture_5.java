


  @Override
  public OperationFuture<T> addListener(OperationFutureListener listener) {
    super.addToListeners((GenericFutureListener) listener);
    return this;
  }

  @Override
  public OperationFuture<T> removeListener(OperationFutureListener listener) {
    super.removeFromListeners((GenericFutureListener) listener);
    return this;
  }

