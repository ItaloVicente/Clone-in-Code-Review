   public OperationFuture<Boolean> add(String key, Object value, PersistTo req, ReplicateTo rep) {
      return this.add(key, 0, value, req, rep);
   }

  public OperationFuture<Boolean> replace(String key, Object value) {
      return replace(key, 0, value);
  }

