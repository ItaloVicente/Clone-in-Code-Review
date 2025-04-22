		String repoName = Activator.getDefault().getRepositoryUtil().getRepositoryName(repository);
		RepositoryState state = repository.getRepositoryState();
		if (state != RepositoryState.SAFE)
			repositoryName = repoName + '|' + state.getDescription();
		else
			repositoryName = repoName;
