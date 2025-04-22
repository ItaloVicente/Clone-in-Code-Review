		} else if (state.equals(RepositoryState.MERGING_RESOLVED)) {
			isMergedResolved = true;
			mergeRepository = repo;
		} else if (state.equals(RepositoryState.CHERRY_PICKING_RESOLVED)) {
			isCherryPickResolved = true;
			mergeRepository = repo;
