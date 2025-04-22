			}
		}
		for (File pathToClean : pathsToClean) {
			if (pathToClean.exists()) {
				FileUtils.delete(pathToClean,
						FileUtils.RECURSIVE | FileUtils.RETRY);
			}
		}
		shutDownRepositories();
	}

	protected static void shutDownRepositories() {
		RepositoryCache cache = Activator.getDefault().getRepositoryCache();
		for (Repository repository : cache.getAllRepositories()) {
			repository.close();
		}
		cache.clear();
