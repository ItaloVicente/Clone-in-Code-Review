		if (showLocationIsSetOnCommandLine) {
			Stream.of(showLocationPathInTitle, workspacePath).forEach(c -> {
				c.setEnabled(false);
				c.setToolTipText(IDEWorkbenchMessages.IDEWorkspacePreference_showLocationSetOnCommandLine);
			});
		}
