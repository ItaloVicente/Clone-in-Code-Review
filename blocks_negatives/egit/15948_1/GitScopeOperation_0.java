	private boolean hasChanged(IResource resource) {
		boolean hasChanged = false;
		RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
		try {
			Repository repository = mapping.getRepository();
			Status repoStatus = new Git(repository).status().call();
			String path = resource.getFullPath().removeFirstSegments(1)
					.toOSString();
			hasChanged = repoStatus.getAdded().contains(path)
					|| repoStatus.getChanged().contains(path)
					|| repoStatus.getModified().contains(path)
					|| repoStatus.getRemoved().contains(path)
					|| repoStatus.getUntracked().contains(path);
		} catch (Exception e) {
			Activator.logError(UIText.GitScopeOperation_couldNotDetermineState,
					e);
