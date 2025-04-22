
	/**
	 * Sets a {@link CredentialsProvider}
	 *
	 * @param credentialsProvider
	 *            the provider to use when querying for credentials (eg., during
	 *            signing)
	 * @return {@code this}
	 * @since 5.11
	 */
	public TagCommand setCredentialsProvider(
			CredentialsProvider credentialsProvider) {
		checkCallable();
		this.credentialsProvider = credentialsProvider;
		return this;
	}

