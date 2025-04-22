	public CommitCommand setGpgSigner(GpgSigner signer) {
		checkCallable();
		this.gpgSigner = signer;
		return this;
	}

	public CommitCommand setGpgConfig(GpgConfig config) {
		checkCallable();
		this.gpgConfig = config;
		return this;
	}

