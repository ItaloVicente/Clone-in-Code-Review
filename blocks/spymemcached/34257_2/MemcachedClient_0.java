  @Override
  public OperationFuture<Long> asyncDecr(String key, long by) {
    return asyncMutate(Mutator.decr, key, by, 0, 0);
  }

  @Override
  public OperationFuture<Long> asyncDecr(String key, int by) {
    return asyncMutate(Mutator.decr, key, by, 0, 0);
  }

