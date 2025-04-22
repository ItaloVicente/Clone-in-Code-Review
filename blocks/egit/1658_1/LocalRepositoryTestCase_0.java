	private static void shutDownRepositories() {
		RepositoryCache cache = Activator.getDefault().getRepositoryCache();
		for(Repository repository:cache.getAllRepositories())
			repository.close();
		cache.clear();
	}

	protected static void deleteRecursive(File dirOrFile) throws IOException {
