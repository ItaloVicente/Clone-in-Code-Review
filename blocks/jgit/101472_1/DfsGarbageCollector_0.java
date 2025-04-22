	public DfsGarbageCollector setWriteReftable(boolean write) {
		writeReftable = write;
		return this;
	}

	public DfsGarbageCollector setRefBlockSize(int szBytes) {
		refBlockSize = szBytes;
		return this;
	}

	public DfsGarbageCollector setRestartInterval(int interval) {
		refRestartInterval = interval;
		return this;
	}

