		wizard.setDialogSettings(wizardSettings);
		wizard.setForcePreviousAndNextButtons(true);

		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize(
				Math.max(SIZING_WIZARD_WIDTH_2, dialog.getShell().getSize().x),
				SIZING_WIZARD_HEIGHT_2);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(), IIDEHelpContextIds.NEW_PROJECT_WIZARD);


		DialogCheck.assertDialog(dialog);
	}

	public void testNewResource() {
		NewWizard wizard = new NewWizard();
		ISelection selection = getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService().getSelection();
		IStructuredSelection selectionToPass = null;
		if (selection instanceof IStructuredSelection) {
