	protected static GitRepositoriesViewTestUtils myRepoViewUtil;

	@Before
	public void setup() {
		setTestUtils();
	}

	private static void setTestUtils() {
		myRepoViewUtil = new GitRepositoriesViewTestUtils();
	}

	@After
	public void teardown() {
		myRepoViewUtil.dispose();
	}
