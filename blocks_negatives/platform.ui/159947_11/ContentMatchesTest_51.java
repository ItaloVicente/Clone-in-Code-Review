	/**
	 * @param testName
	 */
	public ContentMatchesTest() {
		super(ContentMatchesTest.class.getSimpleName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		IWorkbenchWindow window = openTestWindow();
