	private static Set<Repository> getRepositoriesOfResources(
			IResource[] resources) {
		Set<Repository> repos = new HashSet<Repository>();
		for (IResource resource : resources) {
			Repository r = getRepositoryOfMapping(resource);
			repos.add(r);
		}
		return repos;
	}

