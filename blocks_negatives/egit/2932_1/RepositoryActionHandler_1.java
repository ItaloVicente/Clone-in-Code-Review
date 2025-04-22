		Set<Repository> repos = new HashSet<Repository>(selectedProjects.length);
		for (IProject project : selectedProjects) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(project);
			if (repositoryMapping == null)
				return new Repository[0];
			repos.add(repositoryMapping.getRepository());
		}
		return repos.toArray(new Repository[repos.size()]);
