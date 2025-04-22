	/**
	 * @param credentialsProvider
	 *            the {@link CredentialsProvider} to use
	 * @return {@code this}
	 */
	public FetchCommand setCredentialsProvider(
			CredentialsProvider credentialsProvider) {
		checkCallable();
		this.credentialsProvider = credentialsProvider;
		return this;
	}

