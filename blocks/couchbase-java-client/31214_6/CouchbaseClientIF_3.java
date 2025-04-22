  OperationFuture<CASResponse> asyncCas(String key, long cas, Object value,
    PersistTo req, ReplicateTo rep);

  OperationFuture<CASResponse> asyncCas(String key, long cas, Object value,
    PersistTo req);

    ReplicateTo rep);

  OperationFuture<CASResponse> asyncCas(String key, long cas, int exp,
    Object value, PersistTo req);

  OperationFuture<CASResponse> asyncCas(String key, long cas, int exp,
    Object value, ReplicateTo rep);

  OperationFuture<CASResponse> asyncCas(String key, long cas, int exp,
    Object value, PersistTo req, ReplicateTo rep);
