		RepositoryState state = repo.getRepositoryState();
		if (!state.canCommit()) {
			MessageDialog.openError(
					shell,
					UIText.CommitAction_cannotCommit,
					NLS.bind(UIText.CommitAction_repositoryState,
							state.getDescription()));
			return;
		} else if (state.equals(RepositoryState.MERGING_RESOLVED)) {
			isMergedResolved = true;
			mergeRepository = repo;
		} else if (state.equals(RepositoryState.CHERRY_PICKING_RESOLVED)) {
			isCherryPickResolved = true;
			mergeRepository = repo;
