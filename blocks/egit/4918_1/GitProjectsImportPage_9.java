	private boolean hasValidProjects(ProjectFolder projectFolder) {
		List<ProjectRecord> folderProjects = projectFolder.getProjects();
		for (ProjectRecord projectRecord : folderProjects) {
			if(!isProjectInWorkspace(projectRecord.getProjectName()))
				return true;
		}
		return false;
	}
