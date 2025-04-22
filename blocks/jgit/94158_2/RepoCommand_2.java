		this.baseUri = uri;
		return this;
	}

	public RepoCommand setTargetURI(String uri) {
		this.targetUri = URI.create(uri + "/");
