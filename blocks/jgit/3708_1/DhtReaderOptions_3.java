	public DhtReaderOptions setChunkLimit(int maxBytes) {
		chunkLimit = Math.max(1024
		return this;
	}

	public int getOpenQueuePrefetchRatio() {
		return openQueuePrefetchRatio;
	}

	public DhtReaderOptions setOpenQueuePrefetchRatio(int ratio) {
		openQueuePrefetchRatio = Math.max(0
		return this;
	}

	public int getWalkCommitsPrefetchRatio() {
		return walkCommitsPrefetchRatio;
	}

	public DhtReaderOptions setWalkCommitsPrefetchRatio(int ratio) {
		walkCommitsPrefetchRatio = Math.max(0
		return this;
	}

	public int getWalkTreesPrefetchRatio() {
		return walkTreesPrefetchRatio;
	}

	public DhtReaderOptions setWalkTreesPrefetchRatio(int ratio) {
		walkTreesPrefetchRatio = Math.max(0
		return this;
	}

	public int getWriteObjectsPrefetchRatio() {
		return writeObjectsPrefetchRatio;
	}

	public DhtReaderOptions setWriteObjectsPrefetchRatio(int ratio) {
		writeObjectsPrefetchRatio = Math.max(0
