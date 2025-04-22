			diffs.put(repository, indexDiff);
		}
		for (IProject project : projectsInRepositoryOfSelectedResources) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			Repository repository = mapping.getRepository();
			IndexDiff indexDiff = diffs.get(repository);
