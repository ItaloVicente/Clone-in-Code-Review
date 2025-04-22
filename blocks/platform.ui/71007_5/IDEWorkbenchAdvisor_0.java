	private static void setWorkspaceNameDefault() {
		IPreferenceStore preferences = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		String workspaceNameDefault = preferences.getDefaultString(IDEInternalPreferences.WORKSPACE_NAME);
		if (workspaceNameDefault != null)
			return; // Default is set in a plugin customization file - don't change it.
		IPath workspaceDir = Platform.getLocation();
		if (workspaceDir == null)
			return;
		String workspaceName = workspaceDir.lastSegment();
		if (workspaceName == null)
			return;
		preferences.setDefault(IDEInternalPreferences.WORKSPACE_NAME, workspaceName);
	}

