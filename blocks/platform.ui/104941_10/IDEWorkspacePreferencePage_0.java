		if (showLocationIsSetOnCommandLine) {
			Stream.of(showLocationPathInTitle, workspacePath).forEach(c -> {
				c.setEnabled(false);
			});
			pathComposite.setToolTipText(IDEWorkbenchMessages.IDEWorkspacePreference_showLocationSetOnCommandLine);
		}
