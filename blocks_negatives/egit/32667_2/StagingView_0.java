		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
		RepositoryState state = repository.getRepositoryState();
		if (state != RepositoryState.SAFE)
			return repoName + '|' + state.getDescription();
		else
			return repoName;
