
	private final File trash = new File(new File("target")

	@Override
	protected void setUp() throws Exception {
		assertTrue(trash.mkdirs());
	}

	@Override
	protected void tearDown() throws Exception {
		FileUtils.delete(trash
	}

