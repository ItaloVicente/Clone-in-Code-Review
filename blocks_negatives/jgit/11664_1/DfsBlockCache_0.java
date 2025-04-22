	/** Number of bytes to read-ahead from current read position. */
	private final int readAheadLimit;

	/** Thread pool to handle optimistic read-ahead. */
	private final ThreadPoolExecutor readAheadService;

