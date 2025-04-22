		fWizard = getWorkbench().getWorkingSetManager().createWorkingSetNewWizard(null);
		super.doSetUp();
	}

	public void testTypePage() throws Throwable {
		IWizardPage page = fWizardDialog.getCurrentPage();
		WorkingSetDescriptor[] descriptors = getEditableWorkingSetDescriptors();

		assertEquals(descriptors.length > 1, (page instanceof WorkingSetTypePage));

		assertTrue(descriptors.length >= 2);
		if (page instanceof WorkingSetTypePage) {
			WorkingSetTypePage typePage = (WorkingSetTypePage) page;
