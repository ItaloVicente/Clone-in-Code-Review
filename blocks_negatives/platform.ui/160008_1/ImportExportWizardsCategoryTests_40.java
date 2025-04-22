	public void testImportAddToParentedCategory(){
		IWizardCategory newCategory = importRoot.findCategory(
				new Path(WIZARD_IMPORT_NEW_PARENTED_CATEGORY));
		if (newCategory != null){
			IWizardDescriptor wizardDesc =
				newCategory.findWizard(WIZARD_ID_IMPORT_PARENTED_CATEGORY);
			assertTrue(
				"Could not find wizard with id" + WIZARD_ID_IMPORT_PARENTED_CATEGORY+ "in category " + WIZARD_IMPORT_NEW_PARENTED_CATEGORY,
				wizardDesc != null);
