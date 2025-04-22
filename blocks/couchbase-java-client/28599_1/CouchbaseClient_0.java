  protected boolean connectionShutDown() {
    if (mconn instanceof CouchbaseConnection) {
      return ((CouchbaseConnection)mconn).isShutDown();
    } else if (mconn instanceof CouchbaseMemcachedConnection) {
      return ((CouchbaseMemcachedConnection)mconn).isShutDown();
    } else {
      throw new IllegalStateException("Unknown connection type: "
        + mconn.getClass().getCanonicalName());
    }
  }
