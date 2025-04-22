  public void enqueueOperation(String key, Operation o) {
    StringUtils.validateKey(key);
    checkState();
    addOperation(key, o);
  }

