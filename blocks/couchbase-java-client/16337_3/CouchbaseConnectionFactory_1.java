    Config config = getVBucketConfig();
    if (config.getConfigType() == ConfigType.MEMCACHE) {
      return new CouchbaseMemcachedConnection(getReadBufSize(), this, addrs,
        getInitialObservers(), getFailureMode(), getOperationFactory());
    } else if (config.getConfigType() == ConfigType.COUCHBASE) {
      return new CouchbaseConnection(getReadBufSize(), this, addrs,
        getInitialObservers(), getFailureMode(), getOperationFactory());
    }
    throw new IOException("No ConnectionFactory for bucket type "
      + config.getConfigType());
