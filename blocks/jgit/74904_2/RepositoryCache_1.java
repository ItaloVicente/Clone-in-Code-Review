	static boolean isCached(@NonNull Repository repo) {
		File gitDir = repo.getDirectory();
		if (gitDir == null) {
			return false;
		}
		FileKey key = new FileKey(gitDir
		Reference<Repository> repoRef = cache.cacheMap.get(key);
		return repoRef != null && repoRef.get() == repo;
	}

