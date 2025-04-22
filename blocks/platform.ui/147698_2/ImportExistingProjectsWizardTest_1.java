	@Test
	public void test19CloseImportedProjectsZipFile() throws Exception {
		WizardProjectsImportPage wpip = createImportWizardWithZipLocation(
				"ImportExistingProjectsWizardTestRebuildProject");

		try (AutoCloseable restore =
				setPageSetting(wpip, "WizardProjectsImportPage.STORE_CLOSE_CREATED_PROJECTS_ID", true)) {
			wpip.createProjects();

			IProject testProject = ResourcesPlugin.getWorkspace().getRoot()
					.getProject("ImportExistingProjectsWizardTestRebuildProject");
			assertFalse("Expected imported project to be closd due to setting dialog option checkbox",
					testProject.isAccessible());
		}
	}

	@Test
	public void test20FullBuildAfterImportedProjectsZipFile() throws Exception {
		WizardProjectsImportPage wpip = createImportWizardWithZipLocation(
				"ImportExistingProjectsWizardTestRebuildProject");

		ImportTestUtils.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		ImportTestUtils.waitForBuild();

		ImportTestUtils.TestBuilder.assertFullBuildWasDone();
	}

	@Test
	public void test21FullBuildAfterImportedProjects() throws Exception {
		WizardProjectsImportPage wpip = createImportWizardWithDataLocation(
				"ImportExistingProjectsWizardTestRebuildProject"); // located in data/workspaces/

		ImportTestUtils.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		ImportTestUtils.waitForBuild();

		TestBuilder.assertFullBuildWasDone();
	}

	@Test
	public void test22FullBuildAfterImportedProjectsWithCopy() throws Exception {
		WizardProjectsImportPage wpip = createImportWizardWithDataLocation(
				"ImportExistingProjectsWizardTestRebuildProject"); // located in data/workspaces/

		setPageSetting(wpip, "WizardProjectsImportPage.STORE_COPY_PROJECT_ID", true);

		ImportTestUtils.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		ImportTestUtils.waitForBuild();

		TestBuilder.assertFullBuildWasDone();
	}

	private WizardProjectsImportPage createImportWizardWithZipLocation(String testProject) throws Exception {
		zipLocation = ImportTestUtils.copyZipLocation(testProject, testProject); // located in data/workspaces/
		ImportTestUtils.deleteWorkspaceProjects();

		WizardProjectsImportPage wpip = getNewWizard();
		wpip.getProjectFromDirectoryRadio().setSelection(false); // select the other option
		wpip.updateProjectsList(zipLocation);
		selectTestProject(wpip, testProject);

		return wpip;
	}

	private WizardProjectsImportPage createImportWizardWithDataLocation(String testProject) throws Exception {
		dataLocation = ImportTestUtils.copyDataLocation(testProject); // located in data/workspaces/
		ImportTestUtils.deleteWorkspaceProjects();

		WizardProjectsImportPage wpip = getNewWizard();
		wpip.getProjectFromDirectoryRadio().setSelection(true);
		wpip.updateProjectsList(dataLocation);
		selectTestProject(wpip, testProject);

		return wpip;
	}

	private void selectTestProject(WizardProjectsImportPage wpip, String testProject) {
		HashSet<String> projects = new HashSet<>();
		projects.add(testProject);
		ProjectRecord[] selectedProjects = wpip.getProjectRecords();
		ArrayList<String> projectNames = new ArrayList<>();
		for (ProjectRecord selectedProject : selectedProjects) {
			projectNames.add(selectedProject.getProjectName());
		}

		assertTrue("Expected import wizard to find projects: " + projects + ", instead it detects: " + projectNames,
				projectNames.containsAll(projects));

		CheckboxTreeViewer projectsList = wpip.getProjectsList();
		projectsList.setCheckedElements(selectedProjects);
	}

