	private void addProjectsToWorkingSet(Object[] selected) {
		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (int i = 0; i < selected.length; i++) {
			ProjectRecord projectRecord = (ProjectRecord) selected[i];
			IWorkingSet[] selectedWorkingSets = workingSetGroup.getSelectedWorkingSets();
			String projectName = projectRecord.getProjectName();
			IProject project = root.getProject(projectName);
			workingSetManager.addToWorkingSets(project, selectedWorkingSets);
		}
	}

