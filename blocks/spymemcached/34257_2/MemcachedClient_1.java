  public OperationFuture<Long> asyncIncr(String key, long by, long def,
    int exp) {
    return asyncMutate(Mutator.incr, key, by, def, exp);
  }

  @Override
  public OperationFuture<Long> asyncIncr(String key, int by, long def,
    int exp) {
    return asyncMutate(Mutator.incr, key, by, def, exp);
  }

  @Override
  public OperationFuture<Long> asyncDecr(String key, long by, long def,
    int exp) {
    return asyncMutate(Mutator.decr, key, by, def, exp);
