	public PackConfig() {
	}

	public PackConfig(Repository db) {
		fromConfig(db.getConfig());
	}

	public PackConfig(Config cfg) {
		fromConfig(cfg);
	}

	public boolean isReuseDeltas() {
		return reuseDeltas;
	}

	public void setReuseDeltas(boolean reuseDeltas) {
		this.reuseDeltas = reuseDeltas;
	}

	public boolean isReuseObjects() {
		return reuseObjects;
	}

	public void setReuseObjects(boolean reuseObjects) {
		this.reuseObjects = reuseObjects;
	}

	public boolean isDeltaBaseAsOffset() {
		return deltaBaseAsOffset;
	}

	public void setDeltaBaseAsOffset(boolean deltaBaseAsOffset) {
		this.deltaBaseAsOffset = deltaBaseAsOffset;
	}

	public boolean isDeltaCompress() {
		return deltaCompress;
	}

	public void setDeltaCompress(boolean deltaCompress) {
		this.deltaCompress = deltaCompress;
	}

	public int getMaxDeltaDepth() {
		return maxDeltaDepth;
	}

	public void setMaxDeltaDepth(int maxDeltaDepth) {
		this.maxDeltaDepth = maxDeltaDepth;
	}

	public int getDeltaSearchWindowSize() {
		return deltaSearchWindowSize;
	}

	public void setDeltaSearchWindowSize(int objectCount) {
		if (objectCount <= 2)
			setDeltaCompress(false);
		else
			deltaSearchWindowSize = objectCount;
	}

	public long getDeltaSearchMemoryLimit() {
		return deltaSearchMemoryLimit;
	}

	public void setDeltaSearchMemoryLimit(long memoryLimit) {
		deltaSearchMemoryLimit = memoryLimit;
	}

	public long getDeltaCacheSize() {
		return deltaCacheSize;
	}

	public void setDeltaCacheSize(long size) {
		deltaCacheSize = size;
	}

	public int getDeltaCacheLimit() {
		return deltaCacheLimit;
	}

	public void setDeltaCacheLimit(int size) {
		deltaCacheLimit = size;
	}

	public long getBigFileThreshold() {
		return bigFileThreshold;
	}

	public void setBigFileThreshold(long bigFileThreshold) {
		this.bigFileThreshold = bigFileThreshold;
	}

	public int getCompressionLevel() {
		return compressionLevel;
	}

	public void setCompressionLevel(int level) {
		compressionLevel = level;
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public int getIndexVersion() {
		return indexVersion;
	}
