	public InitOperation(@NonNull Repository jGitRepository,
			@NonNull InitParameters parameters) {
		super(new GitFlowRepository(jGitRepository));
		this.develop = parameters.getDevelop();
		this.master = parameters.getMaster();
		this.feature = parameters.getFeature();
		this.release = parameters.getRelease();
		this.hotfix = parameters.getHotfix();
		this.versionTag = parameters.getVersionTag();
	}

