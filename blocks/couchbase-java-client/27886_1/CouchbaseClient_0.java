    return cas(key, cas, 0, value, req, rep);
  }

  @Override
  public CASResponse cas(String key, long cas, int exp,
          Object value, PersistTo req, ReplicateTo rep) {
