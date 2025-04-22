		Set<Repository> repos = new HashSet<Repository>();
		for (IFile file : files) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(file.getProject());
			Repository repo = repositoryMapping.getRepository();
			repos.add(repo);
			String path = repositoryMapping.getRepoRelativePath(file);
