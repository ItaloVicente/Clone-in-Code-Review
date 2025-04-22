  @Override
  public OperationFuture<CASResponse> asyncCas(String key, long cas, int exp,
    Object value, PersistTo req, ReplicateTo rep) {
    return asyncCas(key, cas, exp, value, req, rep, transcoder);
  }

