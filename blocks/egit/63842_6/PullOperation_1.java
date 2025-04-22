		this.configs = Collections.emptyMap();
	}

	public PullOperation(
			@NonNull Map<Repository, PullReferenceConfig> repositories,
			int timeout) {
		this(repositories.keySet(), timeout);
		this.configs = repositories;
