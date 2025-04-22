  public MemcachedClient() {
    this(new DefaultConnectionFactory());
  }

  public MemcachedClient(ConnectionFactory cf) {
    if (cf == null) {
      throw new NullPointerException("Connection factory required");
    }
    if (cf.getOperationTimeout() <= 0) {
      throw new IllegalArgumentException("Operation timeout must be positive.");
    }
    connFactory = cf;
    tcService = new TranscodeService(cf.isDaemon());
    transcoder = cf.getDefaultTranscoder();
    opFact = cf.getOperationFactory();
    assert opFact != null : "Connection factory failed to make op factory";
    operationTimeout = cf.getOperationTimeout();
    authDescriptor = cf.getAuthDescriptor();
  }

  public MemcachedClient connect(InetSocketAddress... ia) throws IOException {
    return connect(Arrays.asList(ia));
  }

  public MemcachedClient connect(List<InetSocketAddress> addrs)
    throws IOException {
    if(isConnected()) {
      return this;
    }

    if (addrs == null) {
      throw new NullPointerException("Server list required");
    }
    if (addrs.isEmpty()) {
      throw new IllegalArgumentException("You must have at least one server to"
        + " connect to");
    }
    mconn = connFactory.createConnection(addrs);
    if (authDescriptor != null) {
      addObserver(this);
    }
    return this;
  }

