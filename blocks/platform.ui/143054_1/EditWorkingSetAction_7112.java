		if (workingSet == null) {
			setEnabled(false);
			return;
		}
		IWorkingSetEditWizard wizard = manager.createWorkingSetEditWizard(workingSet);
		if (wizard == null) {
			String title = WorkbenchMessages.EditWorkingSetAction_error_nowizard_title;
			String message = WorkbenchMessages.EditWorkingSetAction_error_nowizard_message;
			MessageDialog.openError(shell, title, message);
			return;
		}
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.create();
		if (dialog.open() == Window.OK) {
