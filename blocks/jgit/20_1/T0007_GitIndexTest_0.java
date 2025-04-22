	private Repository db;

	private File trash;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		db = createWorkRepository();
		trash = db.getWorkDir();
	}

