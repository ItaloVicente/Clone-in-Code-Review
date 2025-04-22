			IConfigurationElement elem = (IConfigurationElement) settingsIterator
					.next();
			result.add(transferSettings(elem, path));
			selectionIDs[index] = elem.getAttribute(ATT_ID);
		}
		if (result.getSeverity() != IStatus.OK) {
			ErrorDialog
					.openError(
							getShell(),
							IDEWorkbenchMessages.ChooseWorkspaceWithSettingsDialog_TransferFailedMessage,
							IDEWorkbenchMessages.ChooseWorkspaceWithSettingsDialog_SaveSettingsFailed,
							result);
			return;
