		Iterator<IConfigurationElement> settingsIterator = selectedSettings.iterator();
		MultiStatus result = new MultiStatus(PlatformUI.PLUGIN_ID, IStatus.OK,
				IDEWorkbenchMessages.ChooseWorkspaceWithSettingsDialog_ProblemsTransferTitle, null);

		String[] selectionIDs = new String[selectedSettings.size()];
		int index = 0;

		while (settingsIterator.hasNext()) {
			IConfigurationElement elem = settingsIterator.next();
			selectionIDs[index] = elem.getAttribute(ATT_ID);
		}
		if (result.getSeverity() != IStatus.OK) {
			ErrorDialog.openError(getShell(),
					IDEWorkbenchMessages.ChooseWorkspaceWithSettingsDialog_TransferFailedMessage,
					IDEWorkbenchMessages.ChooseWorkspaceWithSettingsDialog_SaveSettingsFailed, result);
			return;
		}
		getDialogSettings().put(ENABLED_TRANSFERS, selectionIDs);
