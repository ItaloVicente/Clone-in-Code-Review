
	/**
	 * After an internal action, see if there are any outstanding SWT events.
	 */
	private void chewUpEvents() {
		Display display = Display.getCurrent();
		while (display.readAndDispatch()) {
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		AdvancedValidationUserApprover.AUTOMATED_MODE = true;
	}

	@Override
	protected void tearDown() throws Exception {
		AdvancedValidationUserApprover.AUTOMATED_MODE = false;
		super.tearDown();
	}
