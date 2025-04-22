  @Override
  public CASResponse cas(String key, long cas, int exp,
          Object value, PersistTo req) {
    return cas(key, cas, exp, value, req, ReplicateTo.ZERO);
  }

