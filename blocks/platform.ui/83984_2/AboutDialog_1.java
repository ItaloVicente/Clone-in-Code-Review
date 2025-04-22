
	private void openInstallationImportWizard() {
		final String installationImportWizard = "org.eclipse.equinox.p2.replication.import"; //$NON-NLS-1$
		IWizardDescriptor wizardDescriptor = PlatformUI.getWorkbench().getImportWizardRegistry()
				.findWizard(installationImportWizard);

		if (wizardDescriptor == null) {
			return;
		}
		try {
			IWorkbenchWizard wizard = wizardDescriptor.createWizard();
			WizardDialog dlg = new WizardDialog(getShell(), wizard);
			dlg.setTitle(wizard.getWindowTitle());
			dlg.setHelpAvailable(false);
			dlg.open();
		} catch (CoreException e) {
			Logger logger = PlatformUI.getWorkbench().getService(Logger.class);
			logger.error(e);
		}

	}

