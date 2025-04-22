  public CASMutator(MemcachedClientIF c, Transcoder<T> tc, int maxTries) {
    super();
    client = c;
    transcoder = tc;
    max = maxTries;
  }
