  @Override
  public OperationFuture<Boolean> add(String key, int exp,
    Object value, PersistTo req, ReplicateTo rep) {
    return add(key, exp, value, req, rep, transcoder);
  }

