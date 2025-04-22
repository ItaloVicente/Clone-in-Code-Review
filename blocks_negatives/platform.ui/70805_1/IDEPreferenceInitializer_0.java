	/**
	 * Returns the name of the workspace directory.
	 */
	private String getWorkspaceDirectoryName() {
		IPath workspaceDir = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		return workspaceDir.lastSegment();
	}

