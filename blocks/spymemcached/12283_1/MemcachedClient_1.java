  public Map<String, Object> getBulk(Iterator<String> keyIter) {
    return getBulk(keyIter, transcoder);
  }

  public <T> Map<String, T> getBulk(Collection<String> keys,
      Transcoder<T> tc) {
    return getBulk(keys.iterator(), tc);
  }

