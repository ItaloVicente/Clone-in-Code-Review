	@Override
	public boolean isEnabled() {
		Repository[] repositories = getRepositories();
		if (repositories.length == 0) {
			return false;
		}
		for (Repository repository : repositories) {
			if (repository.isBare() || SelectionRepositoryStateCache.INSTANCE
					.getHead(repository) == null) {
				return false;
			}
		}
		return true;
	}
