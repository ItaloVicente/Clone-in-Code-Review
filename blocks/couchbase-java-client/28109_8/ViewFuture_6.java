    notifyListeners();
  }

  @Override
  public ViewFuture addListener(HttpCompletionListener listener) {
    super.addToListeners((GenericCompletionListener) listener);
    return this;
  }

  @Override
  public ViewFuture removeListener(HttpCompletionListener listener) {
    super.removeFromListeners((GenericCompletionListener) listener);
    return this;
