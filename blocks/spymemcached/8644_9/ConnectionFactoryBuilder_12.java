  private OperationQueueFactory opQueueFactory;
  private OperationQueueFactory readQueueFactory;
  private OperationQueueFactory writeQueueFactory;

  private Transcoder<Object> transcoder;

  private FailureMode failureMode;

  private Collection<ConnectionObserver> initialObservers =
      Collections.emptyList();

  private OperationFactory opFact;

  private Locator locator = Locator.ARRAY_MOD;
  private long opTimeout = -1;
  private boolean isDaemon = false;
  private boolean shouldOptimize = true;
  private boolean useNagle = false;
  private long maxReconnectDelay =
      DefaultConnectionFactory.DEFAULT_MAX_RECONNECT_DELAY;

  private int readBufSize = -1;
  private HashAlgorithm hashAlg;
  private AuthDescriptor authDescriptor = null;
  private long opQueueMaxBlockTime = -1;

  private int timeoutExceptionThreshold =
      DefaultConnectionFactory.DEFAULT_MAX_TIMEOUTEXCEPTION_THRESHOLD;
  private Config vBucketConfig;


  public ConnectionFactoryBuilder() {
  }

  public ConnectionFactoryBuilder(ConnectionFactory cf) {
    setAuthDescriptor(cf.getAuthDescriptor());
    setDaemon(cf.isDaemon());
    setFailureMode(cf.getFailureMode());
    setHashAlg(cf.getHashAlg());
    setInitialObservers(cf.getInitialObservers());
    setMaxReconnectDelay(cf.getMaxReconnectDelay());
    setOpQueueMaxBlockTime(cf.getOpQueueMaxBlockTime());
    setOpTimeout(cf.getOperationTimeout());
    setReadBufferSize(cf.getReadBufSize());
    setShouldOptimize(cf.shouldOptimize());
    setTimeoutExceptionThreshold(cf.getTimeoutExceptionThreshold());
    setTranscoder(cf.getDefaultTranscoder());
    setUseNagleAlgorithm(cf.useNagleAlgorithm());
  }

  public ConnectionFactoryBuilder setOpQueueFactory(OperationQueueFactory q) {
    opQueueFactory = q;
    return this;
  }

  public ConnectionFactoryBuilder
  setReadOpQueueFactory(OperationQueueFactory q) {
    readQueueFactory = q;
    return this;
  }

  public ConnectionFactoryBuilder
  setWriteOpQueueFactory(OperationQueueFactory q) {
    writeQueueFactory = q;
    return this;
  }

  public ConnectionFactoryBuilder setOpQueueMaxBlockTime(long t) {
    opQueueMaxBlockTime = t;
    return this;
  }

  public ConnectionFactoryBuilder setTranscoder(Transcoder<Object> t) {
    transcoder = t;
    return this;
  }

  public ConnectionFactoryBuilder setFailureMode(FailureMode fm) {
    failureMode = fm;
    return this;
  }

  public ConnectionFactoryBuilder setInitialObservers(
      Collection<ConnectionObserver> obs) {
    initialObservers = obs;
    return this;
  }

  public ConnectionFactoryBuilder setOpFact(OperationFactory f) {
    opFact = f;
    return this;
  }

  public ConnectionFactoryBuilder setOpTimeout(long t) {
    opTimeout = t;
    return this;
  }

  public ConnectionFactoryBuilder setDaemon(boolean d) {
    isDaemon = d;
    return this;
  }

  public ConnectionFactoryBuilder setShouldOptimize(boolean o) {
    shouldOptimize = o;
    return this;
  }

  public ConnectionFactoryBuilder setReadBufferSize(int to) {
    readBufSize = to;
    return this;
  }

  public ConnectionFactoryBuilder setHashAlg(HashAlgorithm to) {
    hashAlg = to;
    return this;
  }

  public ConnectionFactoryBuilder setUseNagleAlgorithm(boolean to) {
    useNagle = to;
    return this;
  }

  public ConnectionFactoryBuilder setProtocol(Protocol prot) {
    switch (prot) {
    case TEXT:
      opFact = new AsciiOperationFactory();
      break;
    case BINARY:
      opFact = new BinaryOperationFactory();
      break;
    default:
      assert false : "Unhandled protocol: " + prot;
