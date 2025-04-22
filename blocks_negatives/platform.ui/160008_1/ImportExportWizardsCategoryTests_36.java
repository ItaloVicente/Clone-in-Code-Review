	public ImportExportWizardsCategoryTests() {
		super(ImportExportWizardsCategoryTests.class.getSimpleName());
		exportRoot = WorkbenchPlugin.getDefault()
			.getExportWizardRegistry().getRootCategory();
		importRoot = WorkbenchPlugin.getDefault()
			.getImportWizardRegistry().getRootCategory();
