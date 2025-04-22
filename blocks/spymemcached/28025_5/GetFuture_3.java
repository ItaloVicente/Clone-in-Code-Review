
  @Override
  public GetFuture<T> addListener(GetFutureListener listener) {
    super.addToListeners((GenericFutureListener) listener);
    return this;
  }

  @Override
  public GetFuture<T> removeListener(GetFutureListener listener) {
    super.removeFromListeners((GenericFutureListener) listener);
    return this;
  }

