	/**
	 * Constructor for IEditorPartTest
	 */
	public EditorActionBarsTest() {
		super(EditorActionBarsTest.class.getSimpleName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		fWindow = openTestWindow();
