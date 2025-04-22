	}

	public DiscardChangesOperation(Collection<IPath> paths) {
		this(ResourceUtil.splitPathsByRepository(paths));
	}

	private DiscardChangesOperation(
			Map<Repository, Collection<String>> pathsByRepository) {
		this.pathsByRepository = pathsByRepository;
		this.schedulingRule = RuleUtil.getRuleForRepositories(pathsByRepository
				.keySet());
	}

	public void setStage(Stage stage) {
		if (revision != null)
			throw new IllegalStateException(
					"Either stage or revision can be set, but not both"); //$NON-NLS-1$
		this.stage = stage;
