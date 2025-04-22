	static void clearExpired() {
		cache.clearAllExpired();
	}

	static void reconfigure(RepositoryCacheConfig repositoryCacheConfig) {
		cache.configureEviction(repositoryCacheConfig);
	}

