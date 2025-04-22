  public CouchbaseConnection(CouchbaseConnectionFactory cf,
      List<InetSocketAddress> addrs, Collection<ConnectionObserver> obs)
    throws IOException {
    super(cf.getReadBufSize(), cf, addrs, obs, cf.getFailureMode(),
        cf.getOperationFactory());
  }

