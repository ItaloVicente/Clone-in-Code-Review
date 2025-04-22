	private static final String FILE1 = "file1";

	protected Git git;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.git = new Git(db);
	}

