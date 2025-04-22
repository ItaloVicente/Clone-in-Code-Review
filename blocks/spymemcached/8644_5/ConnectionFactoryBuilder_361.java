    return this;
  }

  public ConnectionFactoryBuilder setLocatorType(Locator l) {
    locator = l;
    return this;
  }

  public ConnectionFactoryBuilder setMaxReconnectDelay(long to) {
    assert to > 0 : "Reconnect delay must be a positive number";
    maxReconnectDelay = to;
    return this;
  }

  public ConnectionFactoryBuilder setAuthDescriptor(AuthDescriptor to) {
    authDescriptor = to;
    return this;
  }

  public ConnectionFactoryBuilder setTimeoutExceptionThreshold(int to) {
    assert to > 1 : "Minimum timeout exception threshold is 2";
    if (to > 1) {
      timeoutExceptionThreshold = to - 2;
