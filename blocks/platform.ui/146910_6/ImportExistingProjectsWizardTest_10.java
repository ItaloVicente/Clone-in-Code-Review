
	private static void disableWorkspaceAutoBuild() throws CoreException {
		setWorkspaceAutoBuild(false);
	}

	private static void setWorkspaceAutoBuild(boolean autobuildOn) throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IWorkspaceDescription description = workspace.getDescription();
		boolean oldAutoBuildingState = description.isAutoBuilding();
		if (oldAutoBuildingState != autobuildOn) {
			description.setAutoBuilding(autobuildOn);
			workspace.setDescription(description);
		}
	}

	private static void deleteWorkspaceProjects() throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] workspaceProjects = root.getProjects();
		for (IProject workspaceProject : workspaceProjects) {
			FileUtil.deleteProject(workspaceProject);
		}
	}

