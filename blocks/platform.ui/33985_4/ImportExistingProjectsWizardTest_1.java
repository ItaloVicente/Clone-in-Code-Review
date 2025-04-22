
	public void testGetProjectRecordsShouldHandleCorruptProjects()
			throws Exception {

		URL projectsArchive = Platform.asLocalURL(Platform.find(TestPlugin
				.getDefault().getBundle(), new Path(DATA_PATH_PREFIX
				+ CORRUPT_PROJECTS_ARCHIVE + ".zip")));

		WizardProjectsImportPage newWizard = spy(getNewWizard());
		ProjectRecord[] projectRecords = getProjectsFromArchive(newWizard, projectsArchive);
		verify(newWizard).setMessage(DataTransferMessages.WizardProjectsImportPage_projectsInvalid, WARNING);

		List<String> invalidProjectNames = getInvalidProjects(projectRecords);
		assertEquals("Expected to find invalid projects", 2, invalidProjectNames.size());
		assertEquals("Expected pseudo name for first invalid project",
				DataTransferMessages.WizardProjectsImportPage_invalidProjectName, invalidProjectNames.get(0));
		assertEquals("Expected pseudo name for second invalid project",
				DataTransferMessages.WizardProjectsImportPage_invalidProjectName, invalidProjectNames.get(1));

		List<String> validProjectNames = getValidProjects(projectRecords);
		assertEquals("Expected to find only one valid project", 1, validProjectNames.size());
		assertEquals("Expected to find valid project", "Project1", validProjectNames.get(0));

	}

	public void testGetProjectRecordsShouldHandleCorruptAndConflictingProjects()
				throws Exception {

		URL projectsArchive = Platform.asLocalURL(Platform.find(TestPlugin
				.getDefault().getBundle(), new Path(DATA_PATH_PREFIX
				+ CORRUPT_PROJECTS_ARCHIVE + ".zip")));

		WizardProjectsImportPage newWizard = spy(getNewWizard());
		FileUtil.createProject("Project1");

		ProjectRecord[] projectRecords = getProjectsFromArchive(newWizard, projectsArchive);
		verify(newWizard).setMessage(DataTransferMessages.WizardProjectsImportPage_projectsInWorkspaceAndInvalid,
				WARNING);

		List<String> invalidProjectNames = getInvalidProjects(projectRecords);
		assertEquals("Expected to find invalid projects", 2, invalidProjectNames.size());

		List<String> validProjectNames = getValidProjects(projectRecords);
		assertEquals("Expected to find one valid project", 1, validProjectNames.size());
		assertEquals("Expected to find valid project", "Project1", validProjectNames.get(0));

		List<String> conflictingProjectNames = getProjectsWithConflicts(projectRecords);
		assertEquals("Expected to find one existing project", 1, conflictingProjectNames.size());
		assertEquals("Expected to find existing project", "Project1", conflictingProjectNames.get(0));

	}

	private ProjectRecord[] getProjectsFromArchive(WizardProjectsImportPage newWizard, URL projectsArchive) {
		newWizard.getProjectFromDirectoryRadio().setSelection(false);
		newWizard.updateProjectsList(projectsArchive.getPath());
		ProjectRecord[] projectRecords = newWizard.getProjectRecords();
		return projectRecords;
	}

	private List<String> getValidProjects(ProjectRecord[] projectRecords) {
		List<String> projectNames = new ArrayList<String>();
		for (int i = 0; i < projectRecords.length; i++) {
			if (!projectRecords[i].isInvalidProject())
				projectNames.add(projectRecords[i].getProjectName());
		}
		return projectNames;
	}

	private List<String> getInvalidProjects(ProjectRecord[] projectRecords) {
		List<String> projectNames = new ArrayList<String>();
		for (int i = 0; i < projectRecords.length; i++) {
			if (projectRecords[i].isInvalidProject())
				projectNames.add(projectRecords[i].getProjectName());
		}
		return projectNames;
	}

	private List<String> getProjectsWithConflicts(ProjectRecord[] projectRecords) {
		List<String> projectNames = new ArrayList<String>();
		for (int i = 0; i < projectRecords.length; i++) {
			if (projectRecords[i].hasConflicts())
				projectNames.add(projectRecords[i].getProjectName());
		}
		return projectNames;
	}
