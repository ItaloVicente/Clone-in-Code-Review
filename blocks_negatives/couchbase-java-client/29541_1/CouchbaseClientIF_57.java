  OperationFuture<Boolean> set(String key, int exp,
          Object value, PersistTo persist);
  OperationFuture<Boolean> set(String key,
          Object value, PersistTo persist);
  OperationFuture<Boolean> set(String key, int exp,
          Object value, ReplicateTo replicate);
