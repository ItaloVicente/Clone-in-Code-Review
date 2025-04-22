
  public CouchbaseConnectionFactoryBuilder setViewTimeout(int timeout) {
    if(timeout < 500) {
      timeout = 500;
      LOGGER.log(Level.WARNING, "ViewTimeout is too short. Overriding "
        + "viewTimeout with threshold of 500ms.");
    } else if(timeout < 2500) {
      LOGGER.log(Level.WARNING, "ViewTimeout is very short, should be "
        + "more than 2500ms.");
    }
    viewTimeout = timeout;
    return this;
  }

