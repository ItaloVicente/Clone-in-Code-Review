		DialogCheck.assertDialog(dialog);
	}

	public void testNewFolder() {
		BasicNewFolderResourceWizard wizard = new BasicNewFolderResourceWizard();
		wizard.init(PlatformUI.getWorkbench(), new StructuredSelection());
		wizard.setNeedsProgressMonitor(true);
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setText(
				"CreateFolderAction_title");
