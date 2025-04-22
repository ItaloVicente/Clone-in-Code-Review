
	private Repository getCached(RepositoryCache cache, Repository repository)
			throws IOException {
		if (repository == null) {
			return null;
		}
		try {
			return cache.lookupRepository(repository.getDirectory());
		} finally {
			repository.close();
		}
	}
