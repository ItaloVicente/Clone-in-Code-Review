	public void testImportAddToNewCategory(){
		IWizardCategory newCategory = importRoot.findCategory(
				new Path(WIZARD_IMPORT_NEW_CATEGORY));
		if (newCategory != null){
			IWizardDescriptor wizardDesc =
				newCategory.findWizard(WIZARD_ID_IMPORT_NEW_CATEGORY);
			assertTrue(
				"Could not find wizard with id" + WIZARD_ID_IMPORT_NEW_CATEGORY+ "in category " + WIZARD_IMPORT_NEW_CATEGORY,
				wizardDesc != null);
