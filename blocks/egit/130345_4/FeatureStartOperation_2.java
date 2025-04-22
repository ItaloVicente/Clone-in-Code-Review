		this.timeoutInSeconds = timeout;
	}

	public FeatureStartOperation(GitFlowRepository repository,
			String featureName) {
		this(repository, featureName, -1);
