  CASResponse cas(String key, long cas, Object value, PersistTo req, ReplicateTo rep);

  CASResponse cas(String key, long cas, int exp, Object value, PersistTo req, ReplicateTo rep);

  CASResponse cas(String key, long cas, Object value, PersistTo req);


  CASResponse cas(String key, long cas, Object value, ReplicateTo rep);

  CASResponse cas(String key, long cas, int exp, Object value, PersistTo req);


  CASResponse cas(String key, long cas, int exp, Object value, ReplicateTo rep);


  OperationFuture<Boolean> delete(String key, PersistTo req);
