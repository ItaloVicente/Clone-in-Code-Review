		synchronized (repositoryCache) {
			for (Reference<Repository> reference : repositoryCache.values()) {
				Repository repository = reference.get();
				if (repository != null) {
					repositories.add(repository);
				}
