	public GitFlowConfig(@NonNull Repository repository) {
		this(repository.getConfig());
	}

	public GitFlowConfig(@NonNull StoredConfig config) {
		this.config = config;
