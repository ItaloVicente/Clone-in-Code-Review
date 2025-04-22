		Iterator settingsIterator = selectedSettings.iterator();
		MultiStatus result = new MultiStatus(
				PlatformUI.PLUGIN_ID,
				IStatus.OK,
				IDEWorkbenchMessages.ChooseWorkspaceWithSettingsDialog_ProblemsTransferTitle,
				null);

		IPath path = new Path(getWorkspaceLocation());
		String[] selectionIDs = new String[selectedSettings.size()];
		int index = 0;
