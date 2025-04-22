
  public OperationFuture<Boolean> add(String key, int exp,
          String value, ReplicateTo rep) {
    return add(key, exp, value, PersistTo.ZERO, rep);
  }

