  public CouchbaseConnectionFactoryBuilder
    setObsPollInterval(long timeInterval) {
    obsPollInterval = timeInterval;
    return this;
  }

  public CouchbaseConnectionFactoryBuilder setObsPollMax(int maxPoll) {
    obsPollMax = maxPoll;
    return this;
  }
