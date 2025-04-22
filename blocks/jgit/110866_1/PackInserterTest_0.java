	private WindowCacheConfig origWindowCacheConfig;

	@Before
	public void setWindowCacheConfig() {
		origWindowCacheConfig = new WindowCacheConfig();
		origWindowCacheConfig.install();
	}

	@After
	public void resetWindowCacheConfig() {
		origWindowCacheConfig.install();
	}

