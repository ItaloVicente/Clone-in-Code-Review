	/**
	 * Constructor for ResourceNavigatorTest.
	 */
	public ResourceNavigatorTest() {
		super(ResourceNavigatorTest.class.getSimpleName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		workbenchWindow = openTestWindow();
