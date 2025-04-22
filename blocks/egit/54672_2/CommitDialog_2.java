		if (!getSeverityItems().isEmpty() && getPreferenceStore()
				.getBoolean(UIPreferences.CHECK_BEFORE_COMMITTING)) {
			CommitWarningDialog dialog = new CommitWarningDialog(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getShell(),
					getSeverityItems());
			if (dialog.open() == Window.CANCEL) {
				return;
			}
		}

