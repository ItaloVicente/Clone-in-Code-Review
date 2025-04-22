	private boolean commitAndPushEnabled(boolean commitEnabled) {
		Repository repo = currentRepository;
		if (repo == null) {
			return false;
		}
		return commitEnabled && !repo.getRepositoryState().isRebasing();
	}

