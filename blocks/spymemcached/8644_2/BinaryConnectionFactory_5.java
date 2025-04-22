  public BinaryConnectionFactory() {
    super();
  }

  public BinaryConnectionFactory(int len, int bufSize) {
    super(len, bufSize);
  }

  public BinaryConnectionFactory(int len, int bufSize, HashAlgorithm hash) {
    super(len, bufSize, hash);
  }

  @Override
  public MemcachedNode createMemcachedNode(SocketAddress sa, SocketChannel c,
      int bufSize) {
    boolean doAuth = false;
    return new BinaryMemcachedNodeImpl(sa, c, bufSize,
        createReadOperationQueue(), createWriteOperationQueue(),
        createOperationQueue(), getOpQueueMaxBlockTime(), doAuth,
        getOperationTimeout());
  }

  @Override
  public OperationFactory getOperationFactory() {
    return new BinaryOperationFactory();
  }
