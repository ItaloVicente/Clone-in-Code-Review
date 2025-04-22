
  OperationFuture<Boolean> set(String key, int exp, Object value,
    PersistTo req);

  OperationFuture<Boolean> set(String key, Object value, PersistTo req);

  OperationFuture<Boolean> set(String key, int exp, Object value,
    ReplicateTo rep);

