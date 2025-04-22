	private static Repository getRepositoryOfResources(IResource[] resources) {
		Repository repo = null;
		for (IResource resource : resources) {
			Repository r = getRepositoryOfMapping(resource);
			if (r != null && repo != null && r != repo)
				return null;
			else if (r != null)
				repo = r;
		}
		return repo;
