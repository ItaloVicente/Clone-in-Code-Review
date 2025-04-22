			String releaseName) {
		this(repository, findHead(repository), releaseName, true);
	}

	private ReleaseStartOperation(GitFlowRepository repository,
			String startCommitSha1, String releaseName, boolean isHead) {
