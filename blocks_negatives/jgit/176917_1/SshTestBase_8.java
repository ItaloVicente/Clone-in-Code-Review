	protected File defaultCloneDir;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		defaultCloneDir = new File(getTemporaryDirectory(), "cloned");
	}

