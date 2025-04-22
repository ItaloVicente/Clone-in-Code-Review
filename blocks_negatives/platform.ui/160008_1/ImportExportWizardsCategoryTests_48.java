	public void testExportUsingImportCategory(){
		IWizardCategory category = exportRoot.findCategory(
				new Path(WIZARD_IMPORT_NEW_CATEGORY));
		assertTrue(
				"Export wizards should not have category named " + WIZARD_IMPORT_NEW_CATEGORY,
				category == null);
