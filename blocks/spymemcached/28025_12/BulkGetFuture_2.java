
  @Override
  public Future<Map<String, T>> addListener(
    BulkGetCompletionListener listener) {
    super.addToListeners((GenericCompletionListener) listener);
    return this;
  }

  @Override
  public Future<Map<String, T>> removeListener(
    BulkGetCompletionListener listener) {
    super.removeFromListeners((GenericCompletionListener) listener);
    return this;
  }


