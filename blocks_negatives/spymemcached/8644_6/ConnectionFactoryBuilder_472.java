
	public ConnectionFactoryBuilder setOpQueueFactory(OperationQueueFactory q) {
		opQueueFactory = q;
		return this;
	}

	/**
	 * Set the read queue factory.
	 */
	public ConnectionFactoryBuilder setReadOpQueueFactory(OperationQueueFactory q) {
		readQueueFactory = q;
		return this;
	}

	/**
	 * Set the write queue factory.
	 */
	public ConnectionFactoryBuilder setWriteOpQueueFactory(OperationQueueFactory q) {
		writeQueueFactory = q;
		return this;
	}

	/**
	 * Set the maximum amount of time (in milliseconds) a client is willing to
	 * wait for space to become available in an output queue.
	 */
	public ConnectionFactoryBuilder setOpQueueMaxBlockTime(long t) {
		opQueueMaxBlockTime = t;
		return this;
	}

	/**
	 * Set the default transcoder.
	 */
	public ConnectionFactoryBuilder setTranscoder(Transcoder<Object> t) {
		transcoder = t;
		return this;
	}

	/**
	 * Set the failure mode.
	 */
	public ConnectionFactoryBuilder setFailureMode(FailureMode fm) {
		failureMode = fm;
		return this;
	}

	/**
	 * Set the initial connection observers (will observe initial connection).
	 */
	public ConnectionFactoryBuilder setInitialObservers(
			Collection<ConnectionObserver> obs) {
		initialObservers = obs;
		return this;
	}

	/**
	 * Set the operation factory.
	 *
	 * Note that the operation factory is used to also imply the type of
	 * nodes to create.
	 *
	 * @see MemcachedNode
	 */
	public ConnectionFactoryBuilder setOpFact(OperationFactory f) {
		opFact = f;
		return this;
	}

	/**
	 * Set the default operation timeout in milliseconds.
	 */
	public ConnectionFactoryBuilder setOpTimeout(long t) {
		opTimeout = t;
		return this;
	}

	/**
	 * Set the daemon state of the IO thread (defaults to true).
	 */
	public ConnectionFactoryBuilder setDaemon(boolean d) {
		isDaemon = d;
		return this;
	}

	/**
	 * Set to false if the default operation optimization is not desirable.
	 */
	public ConnectionFactoryBuilder setShouldOptimize(boolean o) {
		shouldOptimize = o;
		return this;
	}

	/**
	 * Set the read buffer size.
	 */
	public ConnectionFactoryBuilder setReadBufferSize(int to) {
		readBufSize = to;
		return this;
	}

	/**
	 * Set the hash algorithm.
	 */
	public ConnectionFactoryBuilder setHashAlg(HashAlgorithm to) {
		hashAlg = to;
		return this;
	}

	/**
	 * Set to true if you'd like to enable the Nagle algorithm.
	 */
	public ConnectionFactoryBuilder setUseNagleAlgorithm(boolean to) {
		useNagle = to;
		return this;
	}

	/**
	 * Convenience method to specify the protocol to use.
	 */
	public ConnectionFactoryBuilder setProtocol(Protocol prot) {
		switch(prot) {
			case TEXT:
				opFact = new AsciiOperationFactory();
				break;
			case BINARY:
				opFact = new BinaryOperationFactory();
				break;
			default: assert false : "Unhandled protocol: " + prot;
		}
		return this;
	}

	/**
	 * Set the locator type.
	 */
	public ConnectionFactoryBuilder setLocatorType(Locator l) {
		locator = l;
		return this;
	}

	/**
	 * Set the maximum reconnect delay.
	 */
	public ConnectionFactoryBuilder setMaxReconnectDelay(long to) {
		assert to > 0 : "Reconnect delay must be a positive number";
		maxReconnectDelay = to;
		return this;
	}

	/**
	 * Set the auth descriptor to enable authentication on new connections.
	 */
	public ConnectionFactoryBuilder setAuthDescriptor(AuthDescriptor to) {
		authDescriptor = to;
		return this;
	}

	/**
	 * Set the maximum timeout exception threshold
	 */
	public ConnectionFactoryBuilder setTimeoutExceptionThreshold(int to) {
		assert to > 1 : "Minimum timeout exception threshold is 2";
		if (to > 1) {
			timeoutExceptionThreshold = to -2;
		}
		return this;
	}

    public Config getVBucketConfig() {
        return vBucketConfig;
    }

    public void setVBucketConfig(Config vBucketConfig) {
        this.vBucketConfig = vBucketConfig;
