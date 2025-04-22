
		int dialogResponse = MessageDialog.open(MessageDialog.CONFIRM, parent.getShell(),
				WorkbenchMessages.PreferenceExportWarning_title, WorkbenchMessages.PreferenceExportWarning_message,
				SWT.NONE, WorkbenchMessages.PreferenceExportWarning_applyAndContinue,
				WorkbenchMessages.PreferenceExportWarning_continue);
		if (dialogResponse == -1) {
			return;
		} else if (dialogResponse == 0) {
			okPressed();
		}

