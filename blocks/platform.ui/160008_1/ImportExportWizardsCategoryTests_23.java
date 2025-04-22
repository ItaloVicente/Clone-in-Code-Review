	public void testExportAddToParentedCategory() {
		IWizardCategory newCategory = exportRoot.findCategory(new Path(WIZARD_EXPORT_NEW_PARENTED_CATEGORY));
		if (newCategory != null) {
			IWizardDescriptor wizardDesc = newCategory.findWizard(WIZARD_ID_EXPORT_PARENTED_CATEGORY);
			assertTrue("Could not find wizard with id" + WIZARD_ID_EXPORT_PARENTED_CATEGORY + "in category "
					+ WIZARD_EXPORT_NEW_PARENTED_CATEGORY, wizardDesc != null);
