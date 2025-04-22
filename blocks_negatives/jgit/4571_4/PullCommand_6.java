	/**
	 * @param credentialsProvider
	 *            the {@link CredentialsProvider} to use
	 * @return this instance
	 */
	public PullCommand setCredentialsProvider(
			CredentialsProvider credentialsProvider) {
		checkCallable();
		this.credentialsProvider = credentialsProvider;
		return this;
	}

	/**
	 * @param transportConfigCallback
	 *            if set, the callback will be invoked after the Transport has
	 *            created, but before the Transport is used. The callback can
	 *            use this opportunity to set additional type-specific
	 *            configuration on the Transport instance.
	 * @return {@code this}
	 */
	public PullCommand setTransportConfigCallback(
			TransportConfigCallback transportConfigCallback) {
		checkCallable();
		this.transportConfigCallback = transportConfigCallback;
		return this;
	}

