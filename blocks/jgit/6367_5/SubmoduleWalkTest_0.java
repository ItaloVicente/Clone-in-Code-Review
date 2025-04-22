	private TestRepository<FileRepository> testDb;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		testDb = new TestRepository<FileRepository>(db);
	}
