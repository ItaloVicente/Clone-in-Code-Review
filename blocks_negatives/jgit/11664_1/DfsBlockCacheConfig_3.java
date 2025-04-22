	/** @return number of bytes to read ahead sequentially by. */
	public int getReadAheadLimit() {
		return readAheadLimit;
	}

	/**
	 * @param newSize
	 *            new read-ahead limit, in bytes.
	 * @return {@code this}
	 */
	public DfsBlockCacheConfig setReadAheadLimit(final int newSize) {
		readAheadLimit = Math.max(0, newSize);
		return this;
	}

	/** @return service to perform read-ahead of sequential blocks. */
	public ThreadPoolExecutor getReadAheadService() {
		return readAheadService;
	}

	/**
	 * @param svc
	 *            service to perform read-ahead of sequential blocks with. If
	 *            not null the {@link RejectedExecutionHandler} must be managed
	 *            by the JGit DFS library and not the application.
	 * @return {@code this}.
	 */
	public DfsBlockCacheConfig setReadAheadService(ThreadPoolExecutor svc) {
		if (svc != null)
			svc.setRejectedExecutionHandler(ReadAheadRejectedExecutionHandler.INSTANCE);
		readAheadService = svc;
		return this;
	}

