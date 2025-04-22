	private static void setWorkspaceNameDefault() {
		IPath workspaceDir = Platform.getLocation();
		if (workspaceDir == null)
			return;
		String workspaceName = workspaceDir.lastSegment();
		if (workspaceName == null)
			return;
		IPreferenceStore preferences = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		preferences.setDefault(IDEInternalPreferences.WORKSPACE_NAME, workspaceName);
	}

