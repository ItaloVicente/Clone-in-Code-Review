	private GitDateFormatter dateFormatter;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dateFormatter = new GitDateFormatter(Format.DEFAULT);
	}

