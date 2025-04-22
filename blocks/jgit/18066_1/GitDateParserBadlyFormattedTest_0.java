	@Before
	public void setUp() {
		MockSystemReader mockSystemReader = new MockSystemReader();
		SystemReader.setInstance(mockSystemReader);
	}

	@After
	public void tearDown() {
		SystemReader.setInstance(null);
	}

