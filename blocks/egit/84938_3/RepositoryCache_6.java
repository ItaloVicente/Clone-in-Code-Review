	public void clear() {
		List<File> gitDirs;
		synchronized (repositoryCache) {
			gitDirs = new ArrayList<>(repositoryCache.keySet());
			repositoryCache.clear();
		}
