	static boolean isCached(@NonNull Repository repo) {
		FileKey key = new FileKey(repo.getDirectory()
		Reference<Repository> repoRef = cache.cacheMap.get(key);
		return repoRef != null && repoRef.get() == repo;
	}

