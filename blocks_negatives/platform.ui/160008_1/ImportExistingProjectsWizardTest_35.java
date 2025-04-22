		ImportExportWizard wizard = new ImportExportWizard(
				ImportExportWizard.IMPORT);
		wizard.init(getWorkbench(), null);
		IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault()
				.getDialogSettings();
		IDialogSettings wizardSettings = workbenchSettings
				.getSection("ImportExportTests");
