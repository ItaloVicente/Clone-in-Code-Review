
	private RepositoryMapping findActualRepository(
			Collection<RepositoryMapping> repos, File suggestedRepo) {
		for (RepositoryMapping rm : repos) {
			if (rm.getGitDir().equals(suggestedRepo.getPath()))
				return rm;
		}
		return null;
	}
