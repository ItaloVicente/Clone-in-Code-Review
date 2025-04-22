	/** @return number of recent chunks to hold onto per-reader. */
	public int getRecentChunkCacheSize() {
		return recentChunkCacheSize;
	}

	/**
	 * Set the number of chunks each reader holds onto for recently used access.
	 *
	 * @param chunkCnt
	 *            the number of chunks each reader retains of recently used
	 *            chunks to smooth out access.
	 * @return {@code this}
	 */
	public DhtReaderOptions setRecentChunkCacheSize(int chunkCnt) {
		recentChunkCacheSize = Math.max(1, chunkCnt);
		return this;
	}

