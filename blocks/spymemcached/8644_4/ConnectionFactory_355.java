  MemcachedConnection createConnection(List<InetSocketAddress> addrs)
    throws IOException;

  MemcachedNode createMemcachedNode(SocketAddress sa, SocketChannel c,
      int bufSize);

  BlockingQueue<Operation> createOperationQueue();

  BlockingQueue<Operation> createReadOperationQueue();

  BlockingQueue<Operation> createWriteOperationQueue();

  long getOpQueueMaxBlockTime();

  NodeLocator createLocator(List<MemcachedNode> nodes);

  OperationFactory getOperationFactory();

  long getOperationTimeout();

  boolean isDaemon();

  boolean useNagleAlgorithm();

  Collection<ConnectionObserver> getInitialObservers();

  FailureMode getFailureMode();

  Transcoder<Object> getDefaultTranscoder();

  boolean shouldOptimize();

  int getReadBufSize();

  HashAlgorithm getHashAlg();

  long getMaxReconnectDelay();

  AuthDescriptor getAuthDescriptor();

  int getTimeoutExceptionThreshold();
