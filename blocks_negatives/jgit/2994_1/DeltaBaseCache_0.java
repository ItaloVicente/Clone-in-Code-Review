	static synchronized void reconfigure(final WindowCacheConfig cfg) {
		final int dbLimit = cfg.getDeltaBaseCacheLimit();
		if (maxByteCount != dbLimit) {
			maxByteCount = dbLimit;
			releaseMemory();
		}
