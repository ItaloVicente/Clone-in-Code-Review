	private final AtomicInteger useCnt = new AtomicInteger(1);

	/** Metadata directory holding the repository's critical files. */
	private final File gitDir;

	/** File abstraction used to resolve paths. */
	private final FS fs;

	private final ListenerList myListeners = new ListenerList();

	/** If not bare, the top level directory of the working files. */
	private final File workTree;

	/** If not bare, the index file caching the working file states. */
	private final File indexFile;
