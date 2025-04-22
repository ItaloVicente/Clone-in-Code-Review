	protected static void shutDownRepositories() throws Exception {
		RepositoryCache cache = Activator.getDefault().getRepositoryCache();
		for (Repository repository : cache.getAllRepositories()) {
			repository.close();
		}
		cache.clear();
	}

