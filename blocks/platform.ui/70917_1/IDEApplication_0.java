	private static void setWorkspaceNameDefault() {
		IPath workspaceDir = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		String workspaceName = workspaceDir.lastSegment();
		IPreferenceStore preferences = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		preferences.setDefault(IDEInternalPreferences.WORKSPACE_NAME, workspaceName);
	}

