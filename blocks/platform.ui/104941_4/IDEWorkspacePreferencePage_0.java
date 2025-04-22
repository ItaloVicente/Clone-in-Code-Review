		if (showLocationIsSetOnCommandLine) {
			Stream.of(showLocationPathInTitle, locationLabel, workspacePath).forEach(c -> {
				c.setEnabled(false);
				c.setToolTipText(IDEWorkbenchMessages.IDEWorkspacePreference_showLocationSetOnCommandLine);
			});
		}
