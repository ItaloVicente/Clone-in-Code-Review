		this.repo = repo;
		this.resources = resources;
		paths = new ArrayList<String>();

		RepositoryMapping mapping = RepositoryMapping.findRepositoryMapping(repo);
		for (IResource res : resources)
			paths.add(mapping.getRepoRelativePath(res));
