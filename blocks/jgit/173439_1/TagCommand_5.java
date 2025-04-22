
	public TagCommand setSigningKey(String signingKey) {
		checkCallable();
		this.signingKey = signingKey;
		return this;
	}

	public String getSigningKey() {
		return signingKey;
	}

	public TagCommand setCredentialsProvider(
			CredentialsProvider credentialsProvider) {
		checkCallable();
		this.credentialsProvider = credentialsProvider;
		return this;
	}

