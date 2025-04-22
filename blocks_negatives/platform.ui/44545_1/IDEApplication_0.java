			new ChooseWorkspaceDialog(shell, launchData, false, true) {
				@Override
				protected Shell getParentShell() {
					return null;
				}
			}.prompt(force);
