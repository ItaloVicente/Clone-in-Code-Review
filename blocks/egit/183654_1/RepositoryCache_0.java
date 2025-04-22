	public void removeRepository(final File gitDir) {
		File normalizedGitDir = new Path(gitDir.getAbsolutePath()).toFile();
		synchronized (repositoryCache) {
			RepositoryReference r = repositoryCache.get(normalizedGitDir);
			if (r != null) {
				Closer.closeReference(repositoryCache.remove(normalizedGitDir));
				IndexDiffCache.INSTANCE.remove(normalizedGitDir);
			}
		}
	}

