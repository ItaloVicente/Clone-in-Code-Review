		super(new GitFlowRepository(repository));
		this.develop = develop;
		this.master = master;
		this.feature = featurePrefix;
		this.release = releasePrefix;
		this.hotfix = hotfixPrefix;
		this.versionTag = VERSION_TAG;
