		Iterator settingsIterator = selectedSettings.iterator();
		MultiStatus result = new MultiStatus(
				PlatformUI.PLUGIN_ID,
				IStatus.OK,
				IDEWorkbenchMessages.ChooseWorkspaceWithSettingsDialog_ProblemsTransferTitle,
				null);

		IPath path = new Path(getWorkspaceLocation());
