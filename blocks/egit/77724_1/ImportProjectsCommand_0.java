		if (isSmartImportWizardAvailable()) {
			openSmartImportWizard(event, path);
		} else {
			openGitCreateProjectViaWizardWizard(event, node, path);
		}

		return null;
	}

	private boolean isSmartImportWizardAvailable() {
		final String smartImportWizardId = "org.eclipse.e4.ui.importer.wizard"; //$NON-NLS-1$
		IWizardDescriptor descriptor = PlatformUI.getWorkbench()
				.getImportWizardRegistry().findWizard(smartImportWizardId);
		return descriptor != null;
	}

	private void openSmartImportWizard(ExecutionEvent event, String path) {
		SmartImportWizard wizard = new SmartImportWizard();
		wizard.setInitialImportSource(new File(path));
		WizardDialog dlg = new WizardDialog(getShell(event), wizard);
		dlg.setTitle(wizard.getWindowTitle());
		dlg.setHelpAvailable(false);
		dlg.open();
	}

	private void openGitCreateProjectViaWizardWizard(ExecutionEvent event,
			RepositoryTreeNode node, String path) {
		WizardDialog dlg = new WizardDialog(getShell(event),
				new GitCreateProjectViaWizardWizard(node.getRepository(),
						path)) {
