	
	protected void setUp() throws Exception {
		super.setUp();
		AdvancedValidationUserApprover.AUTOMATED_MODE = true;
	}
	
	protected void tearDown() throws Exception {
		AdvancedValidationUserApprover.AUTOMATED_MODE = false;
		super.tearDown();
	}
