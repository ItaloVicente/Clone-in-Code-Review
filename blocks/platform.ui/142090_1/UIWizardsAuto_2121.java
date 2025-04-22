		wizard.setDialogSettings(wizardSettings);
		wizard.setForcePreviousAndNextButtons(true);
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize(
				Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x),
				SIZING_WIZARD_HEIGHT);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(), IWorkbenchHelpContextIds.EXPORT_WIZARD);

		if (page != null) {
			page.setWizard(wizard);
			dialog.showPage(page);
		}
		return dialog;
	}

	private WizardDialog importWizard(IWizardPage page) {
		ImportWizard wizard = new ImportWizard();
		wizard.init(getWorkbench(), null);
		IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault()
				.getDialogSettings();
		IDialogSettings wizardSettings = workbenchSettings
				.getSection("ImportResourcesAction");
		if (wizardSettings == null) {
