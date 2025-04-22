	public PackConfig(PackConfig cfg) {
		this.compressionLevel = cfg.compressionLevel;
		this.reuseDeltas = cfg.reuseDeltas;
		this.reuseObjects = cfg.reuseObjects;
		this.deltaBaseAsOffset = cfg.deltaBaseAsOffset;
		this.deltaCompress = cfg.deltaCompress;
		this.maxDeltaDepth = cfg.maxDeltaDepth;
		this.deltaSearchWindowSize = cfg.deltaSearchWindowSize;
		this.deltaSearchMemoryLimit = cfg.deltaSearchMemoryLimit;
		this.deltaCacheSize = cfg.deltaCacheSize;
		this.deltaCacheLimit = cfg.deltaCacheLimit;
		this.bigFileThreshold = cfg.bigFileThreshold;
		this.threads = cfg.threads;
		this.executor = cfg.executor;
		this.indexVersion = cfg.indexVersion;
	}

