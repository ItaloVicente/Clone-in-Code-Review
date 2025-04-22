			shell.setAlpha(0);
			try {
				new ChooseWorkspaceDialog(shell, launchData, false, true) {
					@Override
					protected Shell getParentShell() {
						return null;
					}
				}.prompt(force);
			} finally {
				shell.setAlpha(255);
			}
