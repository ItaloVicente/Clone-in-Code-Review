		IWizardDescriptor descriptor = findSmartImportWizardDescriptor();
		if (descriptor != null) {
			openSmartImportWizard(event, descriptor, path);
		} else {
			openGitCreateProjectViaWizardWizard(event, node, path);
		}

		return null;
	}

	private IWizardDescriptor findSmartImportWizardDescriptor() {
		final String smartImportWizardId = "org.eclipse.e4.ui.importer.wizard"; //$NON-NLS-1$
		return PlatformUI.getWorkbench().getImportWizardRegistry()
				.findWizard(smartImportWizardId);
	}

	private void openSmartImportWizard(ExecutionEvent event,
			IWizardDescriptor descriptor, String path)
			throws ExecutionException {
		try {
			IWorkbenchWizard wizard = descriptor.createWizard();
			wizard.init(PlatformUI.getWorkbench(),
					new StructuredSelection(new File(path)));
			WizardDialog dlg = new WizardDialog(getShell(event), wizard);
			dlg.setTitle(wizard.getWindowTitle());
			dlg.setHelpAvailable(false);
			dlg.open();
		} catch (CoreException e) {
			throw new ExecutionException(
					"Error during opening smart import wizard.", e); //$NON-NLS-1$
		}
	}

	private void openGitCreateProjectViaWizardWizard(ExecutionEvent event,
			RepositoryTreeNode node, String path) {
		WizardDialog dlg = new WizardDialog(getShell(event),
				new GitCreateProjectViaWizardWizard(node.getRepository(),
						path)) {
