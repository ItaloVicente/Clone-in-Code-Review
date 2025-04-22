	public void testExportDuplicateCategory() {
		IWizardCategory newCategory = exportRoot.findCategory(new Path(WIZARD_EXPORT_DUPLICATE_CATEGORY));
		if (newCategory != null) {
			IWizardDescriptor wizardDesc = newCategory.findWizard(WIZARD_ID_EXPORT_DUPLICATE_CATEGORY);
			assertTrue("Could not find wizard with id" + WIZARD_ID_EXPORT_DUPLICATE_CATEGORY + "in category "
					+ WIZARD_EXPORT_DUPLICATE_CATEGORY, wizardDesc != null);
