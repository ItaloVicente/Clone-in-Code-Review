
  @Override
  public Future<Map<String, T>> addListener(BulkGetFutureListener listener) {
    super.addToListeners((GenericFutureListener) listener);
    return this;
  }

  @Override
  public Future<Map<String, T>> removeListener(BulkGetFutureListener listener) {
    super.removeFromListeners((GenericFutureListener) listener);
    return this;
  }


