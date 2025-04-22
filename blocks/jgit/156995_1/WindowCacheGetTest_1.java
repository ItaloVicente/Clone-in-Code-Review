	private WindowCacheConfig cfg;
	private boolean useStrongRefs;

	@Parameters(name = "useStrongRefs={0}")
	public static Collection<Object[]> data() {
		return Arrays
				.asList(new Object[][] { { Boolean.TRUE }
	}

	public WindowCacheGetTest(Boolean useStrongRef) {
		this.useStrongRefs = useStrongRef.booleanValue();
	}
