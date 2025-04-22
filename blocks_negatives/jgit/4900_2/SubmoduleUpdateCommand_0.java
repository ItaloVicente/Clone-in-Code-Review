	/**
	 * @param credentialsProvider
	 *            the {@link CredentialsProvider} to use
	 * @return this command
	 */
	public SubmoduleUpdateCommand setCredentialsProvider(
			final CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		return this;
	}

