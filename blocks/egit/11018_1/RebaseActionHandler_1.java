		if (repo == null)
			return false;

		RepositoryState state = repo.getRepositoryState();
		return state == RepositoryState.SAFE || isRebasing(state);
	}

	static boolean isRebasing(RepositoryState state) {
		return state == RepositoryState.REBASING
				|| state == RepositoryState.REBASING_INTERACTIVE
				|| state == RepositoryState.REBASING_MERGE
				|| state == RepositoryState.REBASING_REBASING;
