	private Repository repository;

	@Before
	public void before() throws Exception {
		repository = FileRepositoryBuilder.create(gitDir);
		repository.create();
		connect(project.getProject());
	}

	@After
	public void after() {
		repository.close();
	}

