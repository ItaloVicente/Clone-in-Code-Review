
		RepositoryState state = repo.getRepositoryState();
		if (!state.canCommit())
			throw new WrongRepositoryStateException(
					"Cannot commit on a a repo with state: " + state.name());
		processOptions(state);
