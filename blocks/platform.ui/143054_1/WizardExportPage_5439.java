		MessageDialog.open(MessageDialog.ERROR, getContainer().getShell(),
				IDEWorkbenchMessages.WizardExportPage_errorDialogTitle, message, SWT.SHEET);
	}

	protected void displayResourcesSelectedCount(int selectedResourceCount) {
		if (selectedResourceCount == 1) {
