
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

