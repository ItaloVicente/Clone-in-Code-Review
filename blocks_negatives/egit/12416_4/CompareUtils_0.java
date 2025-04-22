			final Repository repository, final String gitPath,
			String encoding) throws IOException {
		DirCache dc = repository.lockDirCache();
		final DirCacheEntry entry;
		try {
			entry = dc.getEntry(gitPath);
		} finally {
			dc.unlock();
		}

