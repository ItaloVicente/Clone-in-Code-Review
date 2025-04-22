	static DirCache getDirCache(Repository repository) throws IOException {
		synchronized(repoToDirCache) {
			DirCache dirCache = repoToDirCache.get(repository);
			if (dirCache != null && !dirCache.isOutdated())
				return dirCache;
			dirCache = repository.readDirCache();
			repoToDirCache.put(repository, dirCache);
			return dirCache;
		}
	}

