  @Override
  public CASResponse cas(String key, long cas, int exp,
          Object value, ReplicateTo rep) {
    return cas(key, cas, exp, value, PersistTo.ZERO, rep);
  }

  @Override
  public CASResponse cas(String key, long casId, int exp, Object value) {
    return cas(key, casId, exp, value, transcoder);
  }

