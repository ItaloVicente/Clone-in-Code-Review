	private static IndexDiffCache INSTANCE = new IndexDiffCache(
			RepositoryCache.getInstance());

	public static IndexDiffCache getInstance() {
		return INSTANCE;
	}

