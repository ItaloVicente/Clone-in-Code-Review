	@Test
	public void test19CloseImportedProjectsZipFile() throws Exception {
		WizardProjectsImportPage wpip = createImportWizardWithZipLocation(
				"ImportExistingProjectsWizardTestRebuildProject");

		wpip.setCloseProjectsAfterImport(true);
		wpip.createProjects();

		IProject testProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("ImportExistingProjectsWizardTestRebuildProject");
		assertFalse("Expected imported project to be closd due to setting dialog option checkbox",
				testProject.isAccessible());
	}

	@Test
	public void test20RebuildImportedProjectsZipFile() throws Exception {
		WizardProjectsImportPage wpip = createImportWizardWithZipLocation(
				"ImportExistingProjectsWizardTestRebuildProject");

		wpip.setRebuildProjectsAfterImport(true);
		ImportTestUtils.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		ImportTestUtils.waitForBuild();

		ImportTestUtils.TestBuilder.assertCleanFullBuildWasDone();
		ImportTestUtils.assertBinFolderIsCleaned("ImportExistingProjectsWizardTestRebuildProject");
	}

	@Test
	public void test21NoRebuildImportedProjectsZipFile() throws Exception {
		ImportTestUtils.disableWorkspaceAutoBuild();

		WizardProjectsImportPage wpip = createImportWizardWithZipLocation(
				"ImportExistingProjectsWizardTestRebuildProject");
		wpip.setRebuildProjectsAfterImport(false);

		ImportTestUtils.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		ImportTestUtils.waitForBuild();

		ImportTestUtils.TestBuilder.assertNoBuildWasDone();
	}

	@Test
	public void test22RebuildImportedProjects() throws Exception {
		WizardProjectsImportPage wpip = createImportWizardWithDataLocation(
				"ImportExistingProjectsWizardTestRebuildProject"); // located in data/workspaces/

		wpip.setRebuildProjectsAfterImport(true);

		ImportTestUtils.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		ImportTestUtils.waitForBuild();

		TestBuilder.assertCleanFullBuildWasDone();
		ImportTestUtils.assertBinFolderIsCleaned("ImportExistingProjectsWizardTestRebuildProject");
	}

	@Test
	public void test23NoRebuildImportedProjects() throws Exception {
		ImportTestUtils.disableWorkspaceAutoBuild();

		WizardProjectsImportPage wpip = createImportWizardWithDataLocation(
				"ImportExistingProjectsWizardTestRebuildProject"); // located in data/workspaces/

		wpip.setRebuildProjectsAfterImport(false);

		ImportTestUtils.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		ImportTestUtils.waitForBuild();

		ImportTestUtils.TestBuilder.assertNoBuildWasDone();
	}

	@Test
	public void test24RebuildImportedProjectsWithCopy() throws Exception {
		WizardProjectsImportPage wpip = createImportWizardWithDataLocation(
				"ImportExistingProjectsWizardTestRebuildProject"); // located in data/workspaces/

		wpip.setRebuildProjectsAfterImport(true);
		wpip.setCopyProjectsIntoWorkspace(true);

		ImportTestUtils.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		ImportTestUtils.waitForBuild();

		TestBuilder.assertCleanFullBuildWasDone();
		ImportTestUtils.assertBinFolderIsCleaned("ImportExistingProjectsWizardTestRebuildProject");
	}

	@Test
	public void test25NoRebuildImportedProjectsWithCopy() throws Exception {
		ImportTestUtils.disableWorkspaceAutoBuild();

		WizardProjectsImportPage wpip = createImportWizardWithDataLocation(
				"ImportExistingProjectsWizardTestRebuildProject"); // located in data/workspaces/

		wpip.setRebuildProjectsAfterImport(false);
		wpip.setCopyProjectsIntoWorkspace(true);

		ImportTestUtils.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		ImportTestUtils.waitForBuild();

		ImportTestUtils.TestBuilder.assertNoBuildWasDone();
	}

