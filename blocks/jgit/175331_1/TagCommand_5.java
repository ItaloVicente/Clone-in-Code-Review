	public TagCommand setGpgSigner(GpgObjectSigner signer) {
		checkCallable();
		this.gpgSigner = signer;
		return this;
	}

	public TagCommand setGpgConfig(GpgConfig config) {
		checkCallable();
		this.gpgConfig = config;
		return this;
	}

