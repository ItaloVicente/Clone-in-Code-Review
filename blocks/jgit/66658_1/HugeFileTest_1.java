	@Before
	public void before() {
		git = new Git(db);
	}

	@After
	public void after() {
		if (git != null) {
			git.close();
		}
	}

