	/**
	 * @param jGitRepository
	 * @param develop
	 * @param master
	 * @param feature
	 * @param release
	 * @param hotfix
	 * @param versionTag
	 * @deprecated Use {@code InitOperation#InitOperation(Repository, InitParameters)} instead.
	 */
	@Deprecated
	public InitOperation(Repository jGitRepository, String develop,
			String master, String feature, String release, String hotfix,
			String versionTag) {
		super(new GitFlowRepository(jGitRepository));
		this.develop = develop;
		this.master = master;
		this.feature = feature;
		this.release = release;
		this.hotfix = hotfix;
		this.versionTag = versionTag;
	}

