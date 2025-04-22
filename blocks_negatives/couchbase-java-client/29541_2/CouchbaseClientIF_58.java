          Object value, PersistTo persist);
  OperationFuture<Boolean> replace(String key,
          Object value, PersistTo persist);
  OperationFuture<Boolean> replace(String key, int exp,
          Object value, ReplicateTo replicate);
  OperationFuture<Boolean> replace(String key,
          Object value, ReplicateTo replicate);
