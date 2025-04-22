	}

	public DiscardChangesOperation(Collection<IPath> paths) {
		this(ResourceUtil.splitPathsByRepository(paths));
	}

	private DiscardChangesOperation(
			Map<Repository, Collection<String>> pathsByRepository) {
		this.pathsByRepository = pathsByRepository;
		this.schedulingRule = RuleUtil.getRuleForRepositories(pathsByRepository
				.keySet());
