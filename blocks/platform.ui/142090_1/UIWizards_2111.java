	public void testNewFolder() {
		BasicNewFolderResourceWizard wizard = new BasicNewFolderResourceWizard();
		wizard.init(PlatformUI.getWorkbench(), new StructuredSelection());
		wizard.setNeedsProgressMonitor(true);
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setText("CreateFolderAction_title");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
				IIDEHelpContextIds.NEW_FOLDER_WIZARD);
		DialogCheck.assertDialog(dialog);
	}
