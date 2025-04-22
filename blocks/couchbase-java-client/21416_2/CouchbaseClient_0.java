
  public OperationFuture<Boolean> set(String key, int exp,
          String value, ReplicateTo rep) {
    return set(key, exp, value, PersistTo.ZERO, rep);
  }

