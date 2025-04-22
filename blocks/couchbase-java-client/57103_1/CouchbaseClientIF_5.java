  <T> OperationFuture<Boolean> set(String key, int exp,
    T value, PersistTo req, ReplicateTo rep, Transcoder<T> tc);

  <T> OperationFuture<Boolean> add(String key, int exp,
    T value, PersistTo req, ReplicateTo rep, Transcoder<T> tc);


  <T> OperationFuture<Boolean> replace(String key, int exp,
    T value, PersistTo req, ReplicateTo rep, Transcoder<T> tc);

  <T> CASResponse cas(String key, long cas, int exp, T value, PersistTo req,
    ReplicateTo rep, Transcoder<T> tc);

  <T> OperationFuture<CASResponse> asyncCas(String key, long cas, int exp,
    T value, PersistTo req, ReplicateTo rep, Transcoder<T> tc);


