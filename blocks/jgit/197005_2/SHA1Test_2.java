	@DataPoints
	public static Sha1Implementation[] getDataPoints() {
		return new Sha1Implementation[] { Sha1Implementation.JAVA
				Sha1Implementation.JDKNATIVE };
	}

	private Sha1Implementation sha1Implementation;

	public SHA1Test(Sha1Implementation impl) {
		this.sha1Implementation = impl;
	}

	@Before
	public void setUp() throws Exception {
		MockSystemReader mockSystemReader = new MockSystemReader();
		SystemReader.setInstance(mockSystemReader);
			System.setProperty("org.eclipse.jgit.util.sha1.implementation"
					sha1Implementation.name());
	}

	@After
	public void tearDown() {
		SystemReader.setInstance(null);
	}

	@Theory
