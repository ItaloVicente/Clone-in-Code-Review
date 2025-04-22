	private void showRestartDialog() {
		if (0 == MessageDialog.open(MessageDialog.CONFIRM, mainPage.getShell(),
				PreferencesMessages.WizardPreferencesImportRestartDialog_title,
				PreferencesMessages.WizardPreferencesImportRestartDialog_message, SWT.NONE,
				PreferencesMessages.WizardPreferencesImportRestartDialog_ok,
				PreferencesMessages.WizardPreferencesImportRestartDialog_cancel)) {
			Display.getDefault().asyncExec(() -> {
				PlatformUI.getWorkbench().restart();
			});
		}
	}

