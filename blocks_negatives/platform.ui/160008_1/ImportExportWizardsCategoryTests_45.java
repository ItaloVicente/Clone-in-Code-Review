	public void testExportAddToNewCategory(){
		IWizardCategory newCategory = exportRoot.findCategory(
				new Path(WIZARD_EXPORT_NEW_CATEGORY));
		if (newCategory != null){
			IWizardDescriptor wizardDesc =
				newCategory.findWizard(WIZARD_ID_EXPORT_NEW_CATEGORY);
			assertTrue(
				"Could not find wizard with id" + WIZARD_ID_EXPORT_NEW_CATEGORY+ "in category " + WIZARD_EXPORT_NEW_CATEGORY,
				wizardDesc != null);
