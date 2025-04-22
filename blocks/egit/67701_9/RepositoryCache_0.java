	public synchronized Repository getRepository(final File gitDir) {
		prune();
		if (gitDir == null) {
			return null;
		}
		File normalizedGitDir = new Path(gitDir.getAbsolutePath()).toFile();
		Reference<Repository> r = repositoryCache.get(normalizedGitDir);
		return r != null ? r.get() : null;
	}

