	private void openInstallationExportWizard() {
		final String installationExportWizard = "org.eclipse.equinox.p2.replication.export"; //$NON-NLS-1$
		IWizardDescriptor wizardDescriptor = PlatformUI.getWorkbench().getExportWizardRegistry()
				.findWizard(installationExportWizard);

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
			e.printStackTrace();
		}
