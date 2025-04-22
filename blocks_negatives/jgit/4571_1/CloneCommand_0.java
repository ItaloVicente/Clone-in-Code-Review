	/**
	 * @param credentialsProvider
	 *            the {@link CredentialsProvider} to use
	 * @return {@code this}
	 */
	public CloneCommand setCredentialsProvider(
			CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		return this;
	}

	/**
	 * @param timeout
	 *            the timeout used for the fetch step
	 * @return {@code this}
	 */
	public CloneCommand setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

