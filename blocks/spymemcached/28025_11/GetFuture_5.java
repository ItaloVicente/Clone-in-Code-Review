
  @Override
  public GetFuture<T> addListener(GetCompletionListener listener) {
    super.addToListeners((GenericCompletionListener) listener);
    return this;
  }

  @Override
  public GetFuture<T> removeListener(GetCompletionListener listener) {
    super.removeFromListeners((GenericCompletionListener) listener);
    return this;
  }

