		IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
		for (Reference<Repository> repoRef : repositoryCache.values()) {
			Repository repo = repoRef.get();
			if (repo != null) {
				cache.remove(repo);
			}
		}
