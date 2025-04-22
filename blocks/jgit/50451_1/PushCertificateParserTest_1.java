	private Repository db;

	@Before
	public void setUp() {
		db = new InMemoryRepository(new DfsRepositoryDescription("repo"));
	}

	private static SignedPushConfig newEnabledConfig() {
