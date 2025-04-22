		this.timeout = timeout;
	}

	public FeatureStartOperation(GitFlowRepository repository,
			String featureName) {
		this(repository, featureName, -1);
