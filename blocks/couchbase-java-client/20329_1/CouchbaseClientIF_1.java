          String value, PersistTo persist, ReplicateTo replicate)
    throws InterruptedException, ExecutionException;
  OperationFuture<Boolean> add(String key, int exp,
          String value, PersistTo persist)
    throws InterruptedException, ExecutionException;
  OperationFuture<Boolean> add(String key, int exp,
          String value, PersistTo persist, ReplicateTo replicate)
    throws InterruptedException, ExecutionException;
  OperationFuture<Boolean> replace(String key, int exp,
          String value, PersistTo persist)
    throws InterruptedException, ExecutionException;
  OperationFuture<Boolean> replace(String key, int exp,
          String value, PersistTo persist, ReplicateTo replicate)
    throws InterruptedException, ExecutionException;
  CASResponse cas(String key, long cas,
          String value, PersistTo req, ReplicateTo rep)
    throws InterruptedException, ExecutionException;
  CASResponse cas(String key, long cas,
          String value, PersistTo req)
    throws InterruptedException, ExecutionException;
  OperationFuture<Boolean> delete(String key, PersistTo persist)
    throws InterruptedException, ExecutionException;
