					0, IDEWorkbenchMessages.IDEApplication_version_doNotWarnAgain, false) {
				@Override
				protected Shell getParentShell() {
					return null;
				}
			};
			if (isValidShell(shell)) {
				shell.setVisible(false);
			}

