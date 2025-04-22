	/**
	 * Check whether the writer will create new deltas on the fly.
	 * <p>
	 * Default setting: true
	 * </p>
	 *
	 * @return true if the writer will create a new delta when either
	 *         {@link #isReuseDeltas()} is false, or no suitable delta is
	 *         available for reuse.
	 */
	public boolean isDeltaCompress() {
		return deltaCompress;
	}

	/**
	 * Set whether or not the writer will create new deltas on the fly.
	 *
	 * @param deltaCompress
	 *            true to create deltas when {@link #isReuseDeltas()} is false,
	 *            or when a suitable delta isn't available for reuse. Set to
	 *            false to write whole objects instead.
	 */
	public void setDeltaCompress(boolean deltaCompress) {
		this.deltaCompress = deltaCompress;
	}

	/**
	 * Get maximum depth of delta chain set up for this writer. Generated chains
	 * are not longer than this value.
	 * <p>
	 * Default setting: {@link #DEFAULT_MAX_DELTA_DEPTH}
	 * </p>
	 *
	 * @return maximum delta chain depth.
	 */
	public int getMaxDeltaDepth() {
		return maxDeltaDepth;
	}

	/**
	 * Set up maximum depth of delta chain for this writer. Generated chains are
	 * not longer than this value. Too low value causes low compression level,
	 * while too big makes unpacking (reading) longer.
	 * <p>
	 * Default setting: {@link #DEFAULT_MAX_DELTA_DEPTH}
	 * </p>
	 *
	 * @param maxDeltaDepth
	 *            maximum delta chain depth.
	 */
	public void setMaxDeltaDepth(int maxDeltaDepth) {
		this.maxDeltaDepth = maxDeltaDepth;
	}

	/**
	 * Get the number of objects to try when looking for a delta base.
	 * <p>
	 * This limit is per thread, if 4 threads are used the actual memory
	 * used will be 4 times this value.
	 *
	 * @return the object count to be searched.
	 */
	public int getDeltaSearchWindowSize() {
		return deltaSearchWindowSize;
	}

	/**
	 * Set the number of objects considered when searching for a delta base.
	 * <p>
	 * Default setting: {@link #DEFAULT_DELTA_SEARCH_WINDOW_SIZE}
	 * </p>
	 *
	 * @param objectCount
	 *            number of objects to search at once.  Must be at least 2.
	 */
	public void setDeltaSearchWindowSize(int objectCount) {
		if (objectCount <= 2)
			setDeltaCompress(false);
		else
			deltaSearchWindowSize = objectCount;
	}

	/**
	 * Get maximum number of bytes to put into the delta search window.
	 * <p>
	 * Default setting is 0, for an unlimited amount of memory usage. Actual
	 * memory used is the lower limit of either this setting, or the sum of
	 * space used by at most {@link #getDeltaSearchWindowSize()} objects.
	 * <p>
	 * This limit is per thread, if 4 threads are used the actual memory
	 * limit will be 4 times this value.
	 *
	 * @return the memory limit.
	 */
	public long getDeltaSearchMemoryLimit() {
		return deltaSearchMemoryLimit;
	}

	/**
	 * Set the maximum number of bytes to put into the delta search window.
	 * <p>
	 * Default setting is 0, for an unlimited amount of memory usage. If the
	 * memory limit is reached before {@link #getDeltaSearchWindowSize()} the
	 * window size is temporarily lowered.
	 *
	 * @param memoryLimit
	 *            Maximum number of bytes to load at once, 0 for unlimited.
	 */
	public void setDeltaSearchMemoryLimit(long memoryLimit) {
		deltaSearchMemoryLimit = memoryLimit;
	}

	/**
	 * Get the size of the in-memory delta cache.
	 * <p>
	 * This limit is for the entire writer, even if multiple threads are used.
	 *
	 * @return maximum number of bytes worth of delta data to cache in memory.
	 *         If 0 the cache is infinite in size (up to the JVM heap limit
	 *         anyway). A very tiny size such as 1 indicates the cache is
	 *         effectively disabled.
	 */
	public long getDeltaCacheSize() {
		return deltaCacheSize;
	}

	/**
	 * Set the maximum number of bytes of delta data to cache.
	 * <p>
	 * During delta search, up to this many bytes worth of small or hard to
	 * compute deltas will be stored in memory. This cache speeds up writing by
	 * allowing the cached entry to simply be dumped to the output stream.
	 *
	 * @param size
	 *            number of bytes to cache. Set to 0 to enable an infinite
	 *            cache, set to 1 (an impossible size for any delta) to disable
	 *            the cache.
	 */
	public void setDeltaCacheSize(long size) {
		deltaCacheSize = size;
	}

	/**
	 * Maximum size in bytes of a delta to cache.
	 *
	 * @return maximum size (in bytes) of a delta that should be cached.
	 */
	public int getDeltaCacheLimit() {
		return deltaCacheLimit;
	}

	/**
	 * Set the maximum size of a delta that should be cached.
	 * <p>
	 * During delta search, any delta smaller than this size will be cached, up
	 * to the {@link #getDeltaCacheSize()} maximum limit. This speeds up writing
	 * by allowing these cached deltas to be output as-is.
	 *
	 * @param size
	 *            maximum size (in bytes) of a delta to be cached.
	 */
	public void setDeltaCacheLimit(int size) {
		deltaCacheLimit = size;
	}

	/**
	 * Get the maximum file size that will be delta compressed.
	 * <p>
	 * Files bigger than this setting will not be delta compressed, as they are
	 * more than likely already highly compressed binary data files that do not
	 * delta compress well, such as MPEG videos.
	 *
	 * @return the configured big file threshold.
	 */
	public long getBigFileThreshold() {
		return bigFileThreshold;
	}

	/**
	 * Set the maximum file size that should be considered for deltas.
	 *
	 * @param bigFileThreshold
	 *            the limit, in bytes.
	 */
	public void setBigFileThreshold(long bigFileThreshold) {
		this.bigFileThreshold = bigFileThreshold;
	}

	/**
	 * Get the compression level applied to objects in the pack.
	 *
	 * @return current compression level, see {@link java.util.zip.Deflater}.
	 */
	public int getCompressionLevel() {
		return compressionLevel;
	}

	/**
	 * Set the compression level applied to objects in the pack.
	 *
	 * @param level
	 *            compression level, must be a valid level recognized by the
	 *            {@link java.util.zip.Deflater} class. Typically this setting
	 *            is {@link java.util.zip.Deflater#BEST_SPEED}.
	 */
	public void setCompressionLevel(int level) {
		compressionLevel = level;
	}

	/** @return number of threads used for delta compression. */
	public int getThreads() {
		return threads;
	}

	/**
	 * Set the number of threads to use for delta compression.
	 * <p>
	 * During delta compression, if there are enough objects to be considered
	 * the writer will start up concurrent threads and allow them to compress
	 * different sections of the repository concurrently.
	 *
	 * @param threads
	 *            number of threads to use. If <= 0 the number of available
	 *            processors for this JVM is used.
	 */
	public void setThread(int threads) {
		this.threads = threads;
	}

