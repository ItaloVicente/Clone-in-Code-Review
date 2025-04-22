public class DefaultConnectionFactory extends SpyObject implements
    ConnectionFactory {

  public static final FailureMode DEFAULT_FAILURE_MODE =
      FailureMode.Redistribute;

  public static final HashAlgorithm DEFAULT_HASH = HashAlgorithm.NATIVE_HASH;

  public static final int DEFAULT_OP_QUEUE_LEN = 16384;

  public static final long DEFAULT_OP_QUEUE_MAX_BLOCK_TIME = TimeUnit.SECONDS
      .toMillis(10);

  public static final int DEFAULT_READ_BUFFER_SIZE = 16384;

  public static final long DEFAULT_OPERATION_TIMEOUT = 2500;

  public static final long DEFAULT_MAX_RECONNECT_DELAY = 30;

  public static final int DEFAULT_MAX_TIMEOUTEXCEPTION_THRESHOLD = 998;

  protected final int opQueueLen;
  private final int readBufSize;
  private final HashAlgorithm hashAlg;

  public DefaultConnectionFactory(int qLen, int bufSize, HashAlgorithm hash) {
    super();
    opQueueLen = qLen;
    readBufSize = bufSize;
    hashAlg = hash;
  }

  public DefaultConnectionFactory(int qLen, int bufSize) {
    this(qLen, bufSize, DEFAULT_HASH);
  }

  public DefaultConnectionFactory() {
    this(DEFAULT_OP_QUEUE_LEN, DEFAULT_READ_BUFFER_SIZE);
  }

  public MemcachedNode createMemcachedNode(SocketAddress sa, SocketChannel c,
      int bufSize) {

    OperationFactory of = getOperationFactory();
    if (of instanceof AsciiOperationFactory) {
      return new AsciiMemcachedNodeImpl(sa, c, bufSize,
          createReadOperationQueue(),
          createWriteOperationQueue(),
          createOperationQueue(),
          getOpQueueMaxBlockTime(),
          getOperationTimeout());
    } else if (of instanceof BinaryOperationFactory) {
      boolean doAuth = false;
      if (getAuthDescriptor() != null) {
        doAuth = true;
      }
      return new BinaryMemcachedNodeImpl(sa, c, bufSize,
          createReadOperationQueue(),
          createWriteOperationQueue(),
          createOperationQueue(),
          getOpQueueMaxBlockTime(),
          doAuth,
          getOperationTimeout());
    } else {
      throw new IllegalStateException("Unhandled operation factory type " + of);
    }
  }

  public MemcachedConnection createConnection(List<InetSocketAddress> addrs)
    throws IOException {
    return new MemcachedConnection(getReadBufSize(), this, addrs,
        getInitialObservers(), getFailureMode(), getOperationFactory());
  }

  public FailureMode getFailureMode() {
    return DEFAULT_FAILURE_MODE;
  }

  public BlockingQueue<Operation> createOperationQueue() {
    return new ArrayBlockingQueue<Operation>(getOpQueueLen());
  }

  public BlockingQueue<Operation> createReadOperationQueue() {
    return new LinkedBlockingQueue<Operation>();
  }

  public BlockingQueue<Operation> createWriteOperationQueue() {
    return new LinkedBlockingQueue<Operation>();
  }

  public NodeLocator createLocator(List<MemcachedNode> nodes) {
    return new ArrayModNodeLocator(nodes, getHashAlg());
  }

  public int getOpQueueLen() {
    return opQueueLen;
  }

  public long getOpQueueMaxBlockTime() {
    return DEFAULT_OP_QUEUE_MAX_BLOCK_TIME;
  }

  public int getReadBufSize() {
    return readBufSize;
  }

  public HashAlgorithm getHashAlg() {
    return hashAlg;
  }

  public OperationFactory getOperationFactory() {
    return new AsciiOperationFactory();
  }

  public long getOperationTimeout() {
    return DEFAULT_OPERATION_TIMEOUT;
  }

  public boolean isDaemon() {
    return false;
  }

  public Collection<ConnectionObserver> getInitialObservers() {
    return Collections.emptyList();
  }

  public Transcoder<Object> getDefaultTranscoder() {
    return new SerializingTranscoder();
  }

  public boolean useNagleAlgorithm() {
    return false;
  }

  public boolean shouldOptimize() {
    return true;
  }

  public long getMaxReconnectDelay() {
    return DEFAULT_MAX_RECONNECT_DELAY;
  }

  public AuthDescriptor getAuthDescriptor() {
    return null;
  }

  public int getTimeoutExceptionThreshold() {
    return DEFAULT_MAX_TIMEOUTEXCEPTION_THRESHOLD;
  }
