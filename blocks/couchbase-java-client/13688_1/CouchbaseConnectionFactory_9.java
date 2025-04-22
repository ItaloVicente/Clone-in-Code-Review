  public MemcachedConnection createConnection(
      List<InetSocketAddress> addrs) throws IOException {
    return new CouchbaseConnection(this, addrs, getInitialObservers());
  }


  public ViewConnection createViewConnection(
      List<InetSocketAddress> addrs) throws IOException {
    return new ViewConnection(this, addrs, getInitialObservers());
