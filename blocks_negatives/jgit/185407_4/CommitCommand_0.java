
	/**
	 * Sets a {@link CredentialsProvider}
	 *
	 * @param credentialsProvider
	 *            the provider to use when querying for credentials (eg., during
	 *            signing)
	 * @return {@code this}
	 * @since 6.0
	 */
	public CommitCommand setCredentialsProvider(
			CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		return this;
	}
