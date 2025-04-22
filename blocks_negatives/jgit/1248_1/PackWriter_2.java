	/**
	 * Title of {@link ProgressMonitor} task used during counting objects to
	 * pack.
	 *
	 * @see #preparePack(ProgressMonitor, Collection, Collection)
	 */
	public static final String COUNTING_OBJECTS_PROGRESS = JGitText.get().countingObjects;

	/**
	 * Title of {@link ProgressMonitor} task used during compression.
	 *
	 * @see #writePack(ProgressMonitor, ProgressMonitor, OutputStream)
	 */
	public static final String COMPRESSING_OBJECTS_PROGRESS = JGitText.get().compressingObjects;

	/**
	 * Title of {@link ProgressMonitor} task used during writing out pack
	 * (objects)
	 *
	 * @see #writePack(ProgressMonitor, ProgressMonitor, OutputStream)
	 */
	public static final String WRITING_OBJECTS_PROGRESS = JGitText.get().writingObjects;

	/**
	 * Default value of deltas reuse option.
	 *
	 * @see #setReuseDeltas(boolean)
	 */
	public static final boolean DEFAULT_REUSE_DELTAS = true;

	/**
	 * Default value of objects reuse option.
	 *
	 * @see #setReuseObjects(boolean)
	 */
	public static final boolean DEFAULT_REUSE_OBJECTS = true;

	/**
	 * Default value of delta base as offset option.
	 *
	 * @see #setDeltaBaseAsOffset(boolean)
	 */
	public static final boolean DEFAULT_DELTA_BASE_AS_OFFSET = false;

	/**
	 * Default value of maximum delta chain depth.
	 *
	 * @see #setMaxDeltaDepth(int)
	 */
	public static final int DEFAULT_MAX_DELTA_DEPTH = 50;

	/**
	 * Default window size during packing.
	 *
	 * @see #setDeltaSearchWindowSize(int)
	 */
	public static final int DEFAULT_DELTA_SEARCH_WINDOW_SIZE = 10;

	static final long DEFAULT_BIG_FILE_THRESHOLD = 50 * 1024 * 1024;

	static final long DEFAULT_DELTA_CACHE_SIZE = 50 * 1024 * 1024;

	static final int DEFAULT_DELTA_CACHE_LIMIT = 100;

