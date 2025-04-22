	public IPageServiceTest() {
		super(IPageServiceTest.class.getSimpleName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		fWindow = openTestWindow();
