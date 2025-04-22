


  @Override
  public OperationFuture<T> addListener(OperationCompletionListener listener) {
    super.addToListeners((GenericCompletionListener) listener);
    return this;
  }

  @Override
  public OperationFuture<T> removeListener(OperationCompletionListener listener) {
    super.removeFromListeners((GenericCompletionListener) listener);
    return this;
  }

