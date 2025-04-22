	private DirCacheEntry getDirCacheEntry(String path) throws IOException {
		String repoPath = getRepoRelativePath(path);
		DirCache dc = DirCache.read(repository.getIndexFile(), repository.getFS());

		return dc.getEntry(repoPath);
	}
