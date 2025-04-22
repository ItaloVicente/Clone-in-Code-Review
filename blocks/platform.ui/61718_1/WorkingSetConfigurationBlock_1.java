	private void createNewWorkingSet(Shell shell) {
		IWorkingSetManager manager = WorkbenchPlugin.getDefault().getWorkingSetManager();
		IWorkingSetNewWizard wizard = manager.createWorkingSetNewWizard(workingSetTypeIds);
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.create();

		PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
				IWorkbenchHelpContextIds.WORKING_SET_NEW_WIZARD);
		if (dialog.open() == Window.OK) {
			IWorkingSet workingSet = wizard.getSelection();
			if (workingSet != null) {
				manager.addWorkingSet(workingSet);
				selectedWorkingSets = new IWorkingSet[] { workingSet };
				PlatformUI.getWorkbench().getWorkingSetManager().addRecentWorkingSet(workingSet);
			}
			enableButton.setSelection(true);
			updateEnableState(true);
			updateWorkingSetSelection();
		}
	}

