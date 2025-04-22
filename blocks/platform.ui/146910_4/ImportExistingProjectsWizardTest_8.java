	@Test
	public void test19CloseImportedProjectsZipFile() throws Exception {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String zipName = "ImportExistingProjectsWizardTestRebuildProject"; // located in data/workspaces/
		zipLocation = copyZipLocation(zipName, zipName);

		IProject[] workspaceProjects = root.getProjects();
		for (IProject workspaceProject : workspaceProjects) {
			FileUtil.deleteProject(workspaceProject);
		}

		WizardProjectsImportPage wpip = getNewWizard();
		HashSet<String> projects = new HashSet<>();
		projects.add("ImportExistingProjectsWizardTestRebuildProject");

		wpip.getProjectFromDirectoryRadio().setSelection(false); // select the other option
		wpip.setCloseProjectsAfterImport(true);
		wpip.updateProjectsList(zipLocation);

		ProjectRecord[] selectedProjects = wpip.getProjectRecords();
		ArrayList<String> projectNames = new ArrayList<>();
		for (ProjectRecord selectedProject : selectedProjects) {
			projectNames.add(selectedProject.getProjectName());
		}

		assertTrue("Expected import wizard to find projects: " + projects + ", instead it detects: " + projectNames,
				projectNames.containsAll(projects));

		CheckboxTreeViewer projectsList = wpip.getProjectsList();
		projectsList.setCheckedElements(selectedProjects);

		wpip.createProjects();

		IProject testProject = root.getProject("ImportExistingProjectsWizardTestRebuildProject");
		assertFalse("Expected imported project to be closd due to setting dialog option checkbox",
				testProject.isAccessible());
	}

	@Test
	public void test20RebuildImportedProjectsZipFile() throws Exception {
		disableWorkspaceAutoBuild();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String zipName = "ImportExistingProjectsWizardTestRebuildProject"; // located in data/workspaces/
		zipLocation = copyZipLocation(zipName, zipName);

		IProject[] workspaceProjects = root.getProjects();
		for (IProject workspaceProject : workspaceProjects) {
			FileUtil.deleteProject(workspaceProject);
		}

		WizardProjectsImportPage wpip = getNewWizard();
		HashSet<String> projects = new HashSet<>();
		projects.add("ImportExistingProjectsWizardTestRebuildProject");

		wpip.getProjectFromDirectoryRadio().setSelection(false); // select the other option
		wpip.setRebuildProjectsAfterImport(true);
		wpip.updateProjectsList(zipLocation);

		ProjectRecord[] selectedProjects = wpip.getProjectRecords();
		ArrayList<String> projectNames = new ArrayList<>();
		for (ProjectRecord selectedProject : selectedProjects) {
			projectNames.add(selectedProject.getProjectName());
		}

		assertTrue("Expected import wizard to find projects: " + projects + ", instead it detects: " + projectNames,
				projectNames.containsAll(projects));

		CheckboxTreeViewer projectsList = wpip.getProjectsList();
		projectsList.setCheckedElements(selectedProjects);

		ImportExistingProjectsWizardTest.TestBuilder.resetCallCount();
		wpip.createProjects();
		waitForJobs(0, 250);

		assertEquals("Expected build to be triggered for imported project due to setting dialog option checkbox",
				1, ImportExistingProjectsWizardTest.TestBuilder.builderCallCount.get());
	}

	@Test
	public void test21NoRebuildImportedProjectsZipFile() throws Exception {
		disableWorkspaceAutoBuild();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String zipName = "ImportExistingProjectsWizardTestRebuildProject"; // located in data/workspaces/
		zipLocation = copyZipLocation(zipName, zipName);

		IProject[] workspaceProjects = root.getProjects();
		for (IProject workspaceProject : workspaceProjects) {
			FileUtil.deleteProject(workspaceProject);
		}

		WizardProjectsImportPage wpip = getNewWizard();
		HashSet<String> projects = new HashSet<>();
		projects.add("ImportExistingProjectsWizardTestRebuildProject");

		wpip.getProjectFromDirectoryRadio().setSelection(false); // select the other option
		wpip.setRebuildProjectsAfterImport(false);
		wpip.updateProjectsList(zipLocation);

		ProjectRecord[] selectedProjects = wpip.getProjectRecords();
		ArrayList<String> projectNames = new ArrayList<>();
		for (ProjectRecord selectedProject : selectedProjects) {
			projectNames.add(selectedProject.getProjectName());
		}

		assertTrue("Expected import wizard to find projects: " + projects + ", instead it detects: " + projectNames,
				projectNames.containsAll(projects));

		CheckboxTreeViewer projectsList = wpip.getProjectsList();
		projectsList.setCheckedElements(selectedProjects);

		ImportExistingProjectsWizardTest.TestBuilder.resetCallCount();
		wpip.createProjects();

		assertEquals("Expected no build to be triggered for imported project due to unsetting dialog option checkbox",
				0, ImportExistingProjectsWizardTest.TestBuilder.builderCallCount.get());
	}

	@Test
	public void test22RebuildImportedProjects() throws Exception {
		disableWorkspaceAutoBuild();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String zipName = "ImportExistingProjectsWizardTestRebuildProject"; // located in data/workspaces/
		dataLocation = copyDataLocation(zipName);

		IProject[] workspaceProjects = root.getProjects();
		for (IProject workspaceProject : workspaceProjects) {
			FileUtil.deleteProject(workspaceProject);
		}

		WizardProjectsImportPage wpip = getNewWizard();
		HashSet<String> projects = new HashSet<>();
		projects.add("ImportExistingProjectsWizardTestRebuildProject");

		wpip.getProjectFromDirectoryRadio().setSelection(true);
		wpip.setRebuildProjectsAfterImport(true);
		wpip.updateProjectsList(dataLocation);

		ProjectRecord[] selectedProjects = wpip.getProjectRecords();
		ArrayList<String> projectNames = new ArrayList<>();
		for (ProjectRecord selectedProject : selectedProjects) {
			projectNames.add(selectedProject.getProjectName());
		}

		assertTrue("Expected import wizard to find projects: " + projects + ", instead it detects: " + projectNames,
				projectNames.containsAll(projects));

		CheckboxTreeViewer projectsList = wpip.getProjectsList();
		projectsList.setCheckedElements(selectedProjects);

		ImportExistingProjectsWizardTest.TestBuilder.resetCallCount();
		wpip.createProjects();

		assertEquals("Expected build to be triggered for imported project due to setting dialog option checkbox",
				1, ImportExistingProjectsWizardTest.TestBuilder.builderCallCount.get());
	}

	@Test
	public void test23NoRebuildImportedProjects() throws Exception {
		disableWorkspaceAutoBuild();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String zipName = "ImportExistingProjectsWizardTestRebuildProject"; // located in data/workspaces/
		dataLocation = copyDataLocation(zipName);

		IProject[] workspaceProjects = root.getProjects();
		for (IProject workspaceProject : workspaceProjects) {
			FileUtil.deleteProject(workspaceProject);
		}

		WizardProjectsImportPage wpip = getNewWizard();
		HashSet<String> projects = new HashSet<>();
		projects.add("ImportExistingProjectsWizardTestRebuildProject");

		wpip.getProjectFromDirectoryRadio().setSelection(true);
		wpip.setRebuildProjectsAfterImport(false);
		wpip.updateProjectsList(dataLocation);

		ProjectRecord[] selectedProjects = wpip.getProjectRecords();
		ArrayList<String> projectNames = new ArrayList<>();
		for (ProjectRecord selectedProject : selectedProjects) {
			projectNames.add(selectedProject.getProjectName());
		}

		assertTrue("Expected import wizard to find projects: " + projects + ", instead it detects: " + projectNames,
				projectNames.containsAll(projects));

		CheckboxTreeViewer projectsList = wpip.getProjectsList();
		projectsList.setCheckedElements(selectedProjects);

		ImportExistingProjectsWizardTest.TestBuilder.resetCallCount();
		wpip.createProjects();

		assertEquals("Expected no build to be triggered for imported project due to unsetting dialog option checkbox",
				0, ImportExistingProjectsWizardTest.TestBuilder.builderCallCount.get());
	}

