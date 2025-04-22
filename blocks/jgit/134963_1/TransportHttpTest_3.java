	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		final Config config = db.getConfig();
		config.setBoolean("http"
		cookieFile = createTempFile();
		config.setString("http"
				cookieFile.getAbsolutePath());
	}
