	protected void showChooseWorkspaceDialog(ChooseWorkspaceData data) {
		ChooseWorkspaceDialog dialog = new ChooseWorkspaceWithSettingsDialog(
				window.getShell(), data, true, false);
		dialog.prompt(true);
	}

