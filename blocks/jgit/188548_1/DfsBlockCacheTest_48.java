		resetCache(32);
	}

	private void resetCache(int concurrencyLevel) {
		DfsBlockCache.reconfigure(new DfsBlockCacheConfig().setBlockSize(512)
				.setConcurrencyLevel(concurrencyLevel).setBlockLimit(1 << 20));
