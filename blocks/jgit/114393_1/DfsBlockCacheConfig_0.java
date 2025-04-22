	public DfsBlockCacheConfig(DfsBlockCacheConfig cfg) {
		setBlockLimit(cfg.getBlockLimit());
		setBlockSize(cfg.getBlockSize());
		setStreamRatio(cfg.getStreamRatio());
		setConcurrencyLevel(cfg.getConcurrencyLevel());
	}

