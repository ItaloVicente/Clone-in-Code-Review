		mapping = RepositoryMapping.getMapping(resource);
		repository = mapping.getRepository();
		headId = repository.resolve(Constants.HEAD);

		store = Activator.getDefault().getPreferenceStore();
		String repoName = Activator.getDefault().getRepositoryUtil().getRepositoryName(repository);
		RepositoryState state = repository.getRepositoryState();
		if (state != RepositoryState.SAFE)
			repositoryName = repoName + '|' + state.getDescription();
		else
			repositoryName = repoName;

		branch = getShortBranch();

		TreeWalk treeWalk = createThreeWayTreeWalk();
		if (treeWalk == null)
			return;

		switch (resource.getType()) {
		case IResource.FILE:
			if (!treeWalk.next())
