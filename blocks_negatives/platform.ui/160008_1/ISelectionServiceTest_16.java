	public ISelectionServiceTest() {
		super(ISelectionServiceTest.class.getSimpleName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		fWindow = openTestWindow();
