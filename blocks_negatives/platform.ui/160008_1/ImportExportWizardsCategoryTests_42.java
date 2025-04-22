	public void testImportUsingExportCategory(){
		IWizardCategory category = importRoot.findCategory(
				new Path(WIZARD_EXPORT_NEW_CATEGORY));
		assertTrue(
				"Import wizards should not have category named " + WIZARD_EXPORT_NEW_CATEGORY,
				category == null);
