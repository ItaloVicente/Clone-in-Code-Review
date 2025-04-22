	private static final Options NO_REFCACHE = new Options();

	private static final Options WITH_REFCACHE = new Options()
			.setUseRefCache(true);

	@Parameter(0)
	public boolean useRefCache;

	@Parameters(name = "useRefCache={0}")
	public static Collection<Object[]> data() {
				{ Boolean.FALSE }
	}

	@Override
	protected Options getOptions() {
		return useRefCache ? WITH_REFCACHE : NO_REFCACHE;
	}

