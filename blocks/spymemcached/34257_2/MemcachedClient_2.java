  @Override
  public OperationFuture<Long> asyncDecr(String key, int by, long def,
    int exp) {
    return asyncMutate(Mutator.decr, key, by, def, exp);
  }

