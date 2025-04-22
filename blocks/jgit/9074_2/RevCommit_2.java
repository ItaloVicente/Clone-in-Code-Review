	public static void setRevCommitCacheSize(int size) {
		synchronized (commitCache) {
			commitCache.setSizeLimit(size);
		}
	}

