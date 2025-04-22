	public PullCommand setCredentialsProvider(
			CredentialsProvider credentialsProvider) {
		checkCallable();
		this.credentialsProvider = credentialsProvider;
		return this;
	}

