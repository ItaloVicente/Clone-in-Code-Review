	@Before
	public void beforeTestCase() throws Exception {
		Activator.getDefault().getRepositoryCache().clear();
	}

	@After
	public void afterTestCase() throws Exception {
		Activator.getDefault().getRepositoryCache().clear();
	}

