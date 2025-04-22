		boolean merging = false;
		if (repository.getRepositoryState().equals(RepositoryState.MERGING)
				|| repository.getRepositoryState().equals(
						RepositoryState.MERGING_RESOLVED))
			merging = true;

