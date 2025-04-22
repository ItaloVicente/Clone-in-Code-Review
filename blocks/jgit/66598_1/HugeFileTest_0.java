	@Before
	void before() {
		git = new Git(db);
	}

	@After
	void after() {
		if (git != null) {
			git.close();
		}
	}

