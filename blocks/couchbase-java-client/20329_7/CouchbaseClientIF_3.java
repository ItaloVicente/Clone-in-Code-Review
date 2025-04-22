  OperationFuture<Boolean> add(String key, int exp,
          String value, PersistTo persist);
  OperationFuture<Boolean> add(String key, int exp,
          String value, PersistTo persist, ReplicateTo replicate);
  OperationFuture<Boolean> replace(String key, int exp,
          String value, PersistTo persist);
  OperationFuture<Boolean> replace(String key, int exp,
          String value, PersistTo persist, ReplicateTo replicate);
  CASResponse cas(String key, long cas,
          String value, PersistTo req, ReplicateTo rep);
  CASResponse cas(String key, long cas,
          String value, PersistTo req);
