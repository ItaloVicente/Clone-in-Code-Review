		if (isValidShell(shell)) {
			parentShellVisible = shell.getVisible();
			if (parentShellVisible && launchData.getShowDialog()) {
				shell.setVisible(false);
			}
		}
