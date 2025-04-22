		final WindowCacheConfig c = new WindowCacheConfig();
		c.setPackedGitMMAP(true);
		c.setPackedGitWindowSize(8 * 1024);
		c.setPackedGitLimit(10 * MB);
		c.setDeltaBaseCacheLimit(10 * MB);
		c.setStreamFileThreshold(50 * MB);
		c.install();
