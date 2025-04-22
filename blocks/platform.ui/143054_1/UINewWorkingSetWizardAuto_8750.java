		}
	}

	public void testEditPage() throws Throwable {
		WorkingSetRegistry registry = WorkbenchPlugin.getDefault()
				.getWorkingSetRegistry();
		IWizardPage page = fWizardDialog.getCurrentPage();
		IWizardPage defaultEditPage = registry.getDefaultWorkingSetPage();
		String defaultEditPageClassName = defaultEditPage.getClass().getName();
		WorkingSetDescriptor[] descriptors = getEditableWorkingSetDescriptors();

		assertEquals(descriptors.length > 1, (page instanceof WorkingSetTypePage));

		if (page instanceof WorkingSetTypePage) {
