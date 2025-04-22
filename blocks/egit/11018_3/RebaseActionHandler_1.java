		if (repo == null)
			return false;

		RepositoryState state = repo.getRepositoryState();
		return state.isRebasing()
				|| RebaseCurrentRefCommand.isEnabledForState(repo, state);
