	private void showRestartDialog() {
		if (new MessageDialog(null, WorkbenchMessages.ThemeChangeWarningTitle, null,
				WorkbenchMessages.ThemeChangeWarningText,
				MessageDialog.QUESTION, 2, WorkbenchMessages.Workbench_RestartButton,
				WorkbenchMessages.Workbench_DontRestartButton)
						.open() == Window.OK) {
			PlatformUI.getWorkbench().restart();
		}
	}

