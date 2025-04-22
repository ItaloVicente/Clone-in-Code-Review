  public CouchbaseConnectionFactory(List<URI> baseList, String bucketName,
      String usr, String pwd) throws IOException {
    super(baseList, bucketName, usr, pwd);
  }

  public CouchbaseNode createCouchDBNode(InetSocketAddress addr,
      AsyncConnectionManager connMgr) {
    return new CouchbaseNode(addr, connMgr,
        new LinkedBlockingQueue<HttpOperation>(opQueueLen),
        getOpQueueMaxBlockTime(), getOperationTimeout());
  }

  public MemcachedConnection createMemcachedConnection(
      List<InetSocketAddress> addrs) throws IOException {
    return new MemcachedConnection(getReadBufSize(), this, addrs,
        getInitialObservers(), getFailureMode(), getOperationFactory());
  }

  public CouchbaseConnection createCouchDBConnection(
      List<InetSocketAddress> addrs) throws IOException {
    return new CouchbaseConnection(this, addrs, getInitialObservers());
  }
