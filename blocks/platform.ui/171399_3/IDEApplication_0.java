	protected void showChooseWorkspaceDialog(Shell shell, ChooseWorkspaceData launchData, boolean force) {
		new ChooseWorkspaceDialog(shell, launchData, false, true) {
			@Override
			protected Shell getParentShell() {
				return null;
			}

		}.prompt(force);
	}

