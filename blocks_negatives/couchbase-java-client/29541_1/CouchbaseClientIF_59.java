  CASResponse cas(String key, long cas,
          Object value, PersistTo req);
  CASResponse cas(String key, long cas,
          Object value, ReplicateTo rep);
  CASResponse cas(String key, long cas, int exp,
          Object value, PersistTo req);
  CASResponse cas(String key, long cas, int exp,
          Object value, ReplicateTo rep);
