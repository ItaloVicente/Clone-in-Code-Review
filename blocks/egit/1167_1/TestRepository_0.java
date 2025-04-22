		DirCache dc = DirCache.read(repository);

		return dc.getEntry(repoPath) != null;
	}

	public long lastModifiedInIndex(String path) throws IOException {
		String repoPath = getRepoRelativePath(path);
		DirCache dc = DirCache.read(repository);

		return dc.getEntry(repoPath).getLastModified();
	}

	public int getDirCacheEntryLength(String path) throws IOException {
		String repoPath = getRepoRelativePath(path);
		DirCache dc = DirCache.read(repository);

		return dc.getEntry(repoPath).getLength();
