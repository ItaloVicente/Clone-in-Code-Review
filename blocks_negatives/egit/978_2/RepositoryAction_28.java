	protected Repository[] getRepositoriesFor(final IProject[] projects) {
		Set<Repository> ret = new HashSet<Repository>();
		for (IProject project : projects) {
			RepositoryMapping repositoryMapping = RepositoryMapping.getMapping(project);
			if (repositoryMapping == null)
				return new Repository[0];
			ret.add(repositoryMapping.getRepository());
		}
		return ret.toArray(new Repository[ret.size()]);
