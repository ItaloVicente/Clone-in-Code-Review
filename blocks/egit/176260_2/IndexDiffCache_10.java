	private static final Object LOCK = new Object();

	private static volatile IndexDiffCache instance;

	public static IndexDiffCache getInstance() {
		RepositoryCache.getInstance();
		IndexDiffCache cache = instance;
		if (cache == null) {
			synchronized (LOCK) {
				cache = instance;
				if (cache == null) {
					cache = instance = new IndexDiffCache();
				}
			}
		}
		return cache;
	}

