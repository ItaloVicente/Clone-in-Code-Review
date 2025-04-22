		setCredentialsProvider(CredentialsProvider.getDefault());
	}

	/**
	 * Set the <code>credentialsProvider</code>.
	 *
	 * @param credentialsProvider
	 *            the {@link org.eclipse.jgit.transport.CredentialsProvider} to
	 *            use
	 * @return {@code this}
	 */
	public C setCredentialsProvider(
			final CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		return self();
