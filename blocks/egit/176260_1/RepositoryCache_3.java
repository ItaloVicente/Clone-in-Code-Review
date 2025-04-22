	private static final Object LOCK = new Object();

	private static volatile RepositoryCache instance;

	public static RepositoryCache getInstance() {
		RepositoryCache cache = instance;
		if (cache == null) {
			synchronized (LOCK) {
				cache = instance;
				if (cache == null) {
					cache = instance = new RepositoryCache();
				}
			}
		}
		return cache;
	}

