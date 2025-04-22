	private void addProjectsToWorkingSet(Set<ProjectRecord> selected) {
		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (ProjectRecord projectRecord : selected) {
			IWorkingSet[] selectedWorkingSets = workingSetGroup.getSelectedWorkingSets();
			String projectName = projectRecord.getProjectName();
			IProject project = root.getProject(projectName);
			workingSetManager.addToWorkingSets(project, selectedWorkingSets);
		}
	}

