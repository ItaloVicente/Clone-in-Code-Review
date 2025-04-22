		IStatus result = transferSelectedSettings(new Path(getWorkspaceLocation()));
		if (result.getSeverity() != IStatus.OK) {
			ErrorDialog.openError(getShell(),
					IDEWorkbenchMessages.ChooseWorkspaceWithSettingsDialog_TransferFailedMessage,
					IDEWorkbenchMessages.ChooseWorkspaceWithSettingsDialog_SaveSettingsFailed, result);
		} else {
			saveSettings(getSelectedSetting());
		}
		super.okPressed();
	}
