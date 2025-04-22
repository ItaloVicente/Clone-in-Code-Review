	public void testImportCategoryDoesNotExist() {
		IWizardCategory otherCategory = importRoot
				.findCategory(new Path(WizardsRegistryReader.UNCATEGORIZED_WIZARD_CATEGORY));
		if (otherCategory != null) {
			IWizardDescriptor wizardDesc = otherCategory.findWizard(WIZARD_ID_IMPORT_INVALID_CATEGORY);
			assertTrue("Could not find wizard with id" + WIZARD_ID_IMPORT_INVALID_CATEGORY + "in Other category.",
					wizardDesc != null);
