	private void showRestartDialog() {
		Display.getDefault().asyncExec(() -> {
			IWorkbench workbench = PlatformUI.getWorkbench();
			if (0 == MessageDialog.open(MessageDialog.QUESTION_WITH_CANCEL,
					workbench.getActiveWorkbenchWindow().getShell(),
					PreferencesMessages.WizardPreferencesImportRestartDialog_title,
					PreferencesMessages.WizardPreferencesImportRestartDialog_message, SWT.NONE,
					PreferencesMessages.WizardPreferencesImportRestartDialog_ok,
					PreferencesMessages.WizardPreferencesImportRestartDialog_cancel))
				workbench.restart();
		});
	}

