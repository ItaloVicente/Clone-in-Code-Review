  @Override
  public OperationFuture<Boolean> replace(String key, int exp,
    Object value, PersistTo req, ReplicateTo rep) {
    return replace(key, exp, value, req, rep, transcoder);
  }

