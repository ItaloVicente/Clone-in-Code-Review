  @Override
  public CASResponse cas(String key, long cas, int exp, Object value,
    PersistTo req, ReplicateTo rep) {
    return cas(key, cas, exp, value, req, rep, transcoder);
  }

