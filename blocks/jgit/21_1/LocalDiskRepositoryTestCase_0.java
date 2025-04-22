
		final WindowCacheConfig c = new WindowCacheConfig();
		c.setPackedGitLimit(128 * WindowCacheConfig.KB);
		c.setPackedGitWindowSize(8 * WindowCacheConfig.KB);
		c.setPackedGitMMAP(useMMAP);
		c.setDeltaBaseCacheLimit(8 * WindowCacheConfig.KB);
		WindowCache.reconfigure(c);
