		Consumer<SmartImportRootWizardPage> doNothing = page -> {};
		proceedSmartImportWizard(wizard, doNothing);
	}

	private void proceedSmartImportWizard(SmartImportWizard wizard, Consumer<SmartImportRootWizardPage> setSettings)
			throws InterruptedException {
