   public OperationFuture<Boolean> set(String key, Object value, PersistTo req, ReplicateTo rep) {
       return set(key, 0, value, req, rep);
   }

  public OperationFuture<Boolean> add(String key, Object value) {
    return add(key, 0, value);
  }

