  public OperationFuture<Long> asyncIncr(String key, long by, long def) {
    return asyncMutate(Mutator.incr, key, by, def, 0);
  }

  @Override
  public OperationFuture<Long> asyncIncr(String key, int by, long def) {
    return asyncMutate(Mutator.incr, key, by, def, 0);
  }

  @Override
  public OperationFuture<Long> asyncDecr(String key, long by, long def) {
    return asyncMutate(Mutator.decr, key, by, def, 0);
  }

  @Override
  public OperationFuture<Long> asyncDecr(String key, int by, long def) {
    return asyncMutate(Mutator.decr, key, by, def, 0);
