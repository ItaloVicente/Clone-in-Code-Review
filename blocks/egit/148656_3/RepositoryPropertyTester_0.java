		GitFlowConfig config = new GitFlowConfig(
				RepositoryStateCache.INSTANCE.getConfig(repository));
		if (IS_INITIALIZED.equals(property)) {
			return config.isInitialized();
		} else if (HAS_DEFAULT_REMOTE.equals(property)) {
			return config.hasDefaultRemote();
		} else {
			String branch = RepositoryStateCache.INSTANCE
					.getFullBranchName(repository);
			if (branch == null) {
				return false;
			}
			branch = Repository.shortenRefName(branch);
			if (IS_FEATURE.equals(property)) {
				return branch.startsWith(config.getFeaturePrefix());
