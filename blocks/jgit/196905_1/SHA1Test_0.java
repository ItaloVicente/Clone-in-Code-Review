	@DataPoints
	public static Boolean[] getDataPoints() {
		return new Boolean[] { Boolean.FALSE
	}

	private boolean useSha1JDK;

	public SHA1Test(boolean useSha1JDK) {
		this.useSha1JDK = useSha1JDK;
	}

	@Before
	public void setUp() throws Exception {
		MockSystemReader mockSystemReader = new MockSystemReader();
		SystemReader.setInstance(mockSystemReader);
		if (useSha1JDK) {
			System.setProperty("org.eclipse.jgit.util.sha1.jdk"
					Boolean.TRUE.toString());
		}
	}

	@After
	public void tearDown() {
		SystemReader.setInstance(null);
	}

	@Theory
