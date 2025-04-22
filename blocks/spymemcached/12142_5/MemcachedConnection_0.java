  public void enqueueOperation(final String key, final Operation o) {
    StringUtils.validateKey(key);
    checkState();
    addOperation(key, o);
  }

