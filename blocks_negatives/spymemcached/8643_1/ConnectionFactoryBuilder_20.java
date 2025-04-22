	private OperationQueueFactory opQueueFactory;
	private OperationQueueFactory readQueueFactory;
	private OperationQueueFactory writeQueueFactory;

	private Transcoder<Object> transcoder;

	private FailureMode failureMode;

	private Collection<ConnectionObserver> initialObservers
		= Collections.emptyList();

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

	private int timeoutExceptionThreshold = DefaultConnectionFactory.DEFAULT_MAX_TIMEOUTEXCEPTION_THRESHOLD;
    private Config vBucketConfig;
	/**
	 * Set the operation queue factory.
	 */

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
