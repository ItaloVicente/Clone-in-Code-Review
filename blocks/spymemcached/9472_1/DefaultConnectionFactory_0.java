	protected String getName() {
		return "DefaultConnectionFactory";
	}

	@Override
	public String toString() {
		return "Failure Mode: " + getFailureMode().name() + ", Hash Algorithm: "
			+ getHashAlg().name() + " Max Reconnect Delay: "
			+ getMaxReconnectDelay() + ", Max Op Timeout: " + getOperationTimeout()
			+ ", Op Queue Length: " + getOpQueueLen() + ", Op Max Queue Block Time"
			+ getOpQueueMaxBlockTime() + ", Max Timeout Exception Threshold: "
			+ getTimeoutExceptionThreshold() + ", Read Buffer Size: "
			+ getReadBufSize() + ", Transcoder: " + getDefaultTranscoder()
			+ ", Operation Factory: " + getOperationFactory() + " isDaemon: "
			+ isDaemon() + ", Optimized: " + shouldOptimize() + ", Using Nagle: "
			+ useNagleAlgorithm() + ", ConnectionFactory: " + getName();
	}
