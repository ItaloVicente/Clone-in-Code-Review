	private void clearAllExpired() {
		for (Reference<Repository> ref : cacheMap.values()) {
			Repository db = ref.get();
			if (isExpired(db)) {
				RepositoryCache.close(db);
			}
		}
	}

