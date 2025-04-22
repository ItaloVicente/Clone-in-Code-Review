		selectProjectRecordsInFolder(rootFolder);
	}

	private void selectProjectRecordsInFolder(ProjectFolder folder) {
		List<ProjectRecord> projects = folder.getProjects();
		for (ProjectRecord record : projects) {
