		IPath dataLocation = getNewWorkbenchStateLocation(newWorkspaceRoot);

		if (dataLocation == null)
			return noWorkingSettingsStatus();

		dataLocation = dataLocation
