		final DirCacheEntry entry;
		try {
			entry = dc.getEntry(gitPath);
		} finally {
			dc.unlock();
		}
