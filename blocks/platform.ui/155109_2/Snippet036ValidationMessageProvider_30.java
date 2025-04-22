
		display.dispose();
	}

	private static Shell creatateShell() {
		IWizard wizard = new MessageProviderWizard();
		WizardDialog wizardDialog = new WizardDialog(null, wizard);
		wizardDialog.open();

		return wizardDialog.getShell();
