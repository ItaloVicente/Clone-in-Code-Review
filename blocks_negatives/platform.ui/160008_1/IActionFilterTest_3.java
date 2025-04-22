	public IActionFilterTest() {
		super(IActionFilterTest.class.getSimpleName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		fWindow = openTestWindow();
