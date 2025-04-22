	private boolean deltaBaseAsOffset = DEFAULT_DELTA_BASE_AS_OFFSET;

	private boolean deltaCompress = true;

	private int maxDeltaDepth = DEFAULT_MAX_DELTA_DEPTH;

	private int deltaSearchWindowSize = DEFAULT_DELTA_SEARCH_WINDOW_SIZE;

	private long deltaSearchMemoryLimit;

	private long deltaCacheSize = DEFAULT_DELTA_CACHE_SIZE;

	private int deltaCacheLimit = DEFAULT_DELTA_CACHE_LIMIT;

	private int indexVersion;

	private long bigFileThreshold = DEFAULT_BIG_FILE_THRESHOLD;

	private int threads = 1;

	private Executor executor;
