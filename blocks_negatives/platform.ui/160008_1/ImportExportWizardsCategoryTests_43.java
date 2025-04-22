	public void testExportNoCategoryProvided(){
		IWizardCategory otherCategory = exportRoot.findCategory(new Path(
				WizardsRegistryReader.UNCATEGORIZED_WIZARD_CATEGORY));
		if (otherCategory != null){
			IWizardDescriptor wizardDesc =
				otherCategory.findWizard(WIZARD_ID_EXPORT_NO_CATEGORY);
			assertTrue(
				"Could not find wizard with id" + WIZARD_ID_EXPORT_NO_CATEGORY+ "in Other category.",
				wizardDesc != null);
