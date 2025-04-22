
	public CommitCommand setSigningKey(String signingKey) {
		checkCallable();
		this.signingKey = signingKey;
		return this;
	}

	public CommitCommand setSign(Boolean sign) {
		checkCallable();
		this.signCommit = sign;
		return this;
	}

	public void setCredentialsProvider(
			CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
	}
