		return getConfiguredRemote(branch, repository.getConfig());
	}

	public static RemoteConfig getConfiguredRemoteCached(
			Repository repository) {
		String branch = RepositoryStateCache.INSTANCE
				.getFullBranchName(repository);
		if (branch == null) {
			return null;
		}
		branch = Repository.shortenRefName(branch);
		return getConfiguredRemote(branch,
				RepositoryStateCache.INSTANCE.getConfig(repository));
	}
