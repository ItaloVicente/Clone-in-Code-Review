  @Override
  public OperationFuture<Boolean> set(String key, int exp,
    Object value, PersistTo req, ReplicateTo rep) {
    return set(key, exp, value, req, rep, transcoder);
  }

