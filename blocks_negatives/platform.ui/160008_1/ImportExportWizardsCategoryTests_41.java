	public void testImportDuplicateCategory(){
		IWizardCategory newCategory = importRoot.findCategory(
				new Path(WIZARD_IMPORT_DUPLICATE_CATEGORY));
		if (newCategory != null){
			IWizardDescriptor wizardDesc =
				newCategory.findWizard(WIZARD_ID_IMPORT_DUPLICATE_CATEGORY);
			assertTrue(
				"Could not find wizard with id" + WIZARD_ID_IMPORT_DUPLICATE_CATEGORY+ "in category " + WIZARD_IMPORT_DUPLICATE_CATEGORY,
				wizardDesc != null);
