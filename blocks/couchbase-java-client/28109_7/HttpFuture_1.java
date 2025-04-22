
  @Override
  public HttpFuture<T> addListener(HttpCompletionListener listener) {
    super.addToListeners((GenericCompletionListener) listener);
    return this;
  }

  @Override
  public HttpFuture<T> removeListener(HttpCompletionListener listener) {
    super.removeFromListeners((GenericCompletionListener) listener);
    return this;
  }

