
	private AtomicReference<WindowCache> windowCache;

	public FileRepositoryBuilder setWindowCache(WindowCacheConfig cfg) {
		setWindowCache(new AtomicReference<WindowCache>(new WindowCache(cfg)));
		return self();
	}

	public FileRepositoryBuilder setWindowCache(
			AtomicReference<WindowCache> wc) {
		windowCache = wc;
		return self();
	}

	public AtomicReference<WindowCache> getWindowCache() {
		return windowCache;
	}

	@Override
	public FileRepositoryBuilder setup()
			throws IllegalArgumentException
		super.setup();
		if (windowCache == null)
			windowCache = WindowCache.getDefaultCache();
		return self();
	}

