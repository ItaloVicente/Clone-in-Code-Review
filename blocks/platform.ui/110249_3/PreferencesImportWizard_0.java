	private void showRestartDialog() {
		if (0 == MessageDialog.open(MessageDialog.CONFIRM, mainPage.getShell(),
				PreferencesMessages.WizardPreferencesImportRestartDialog_title,
				PreferencesMessages.WizardPreferencesImportRestartDialog_message, SWT.NONE,
				PreferencesMessages.WizardPreferencesImportRestartDialog_ok,
				IDialogConstants.CANCEL_LABEL)) {
			Display.getDefault().asyncExec(() -> {
				PlatformUI.getWorkbench().restart();
			});
		}
	}

