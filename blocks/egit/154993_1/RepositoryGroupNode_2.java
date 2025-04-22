
	public Collection<? extends Repository> getRepositories() {
		LinkedHashSet<Repository> result = new LinkedHashSet<>();
		RepositoryCache repositoryCache = org.eclipse.egit.core.Activator
				.getDefault().getRepositoryCache();
		List<File> repoDirs = getObject().getRepositoryDirectories();
		for (File repoDir : repoDirs) {
			Repository repo = null;
			try {
				repo = repositoryCache.lookupRepository(repoDir);
			} catch (IOException e) {
			}
			if (repo != null) {
				result.add(repo);
			}
		}
		return result;
	}
