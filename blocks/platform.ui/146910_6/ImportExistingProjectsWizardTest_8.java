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
		ImportExistingProjectsWizardTest.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);

		assertEquals("Expected build to be triggered for imported project due to setting dialog option checkbox",
				1, ImportExistingProjectsWizardTest.TestBuilder.builderCallCount.get());
	}

	@Test
	public void test21NoRebuildImportedProjectsZipFile() throws Exception {
		disableWorkspaceAutoBuild();

		WizardProjectsImportPage wpip = createImportWizardWithZipLocation(
				"ImportExistingProjectsWizardTestRebuildProject");
		wpip.setRebuildProjectsAfterImport(false);

		ImportExistingProjectsWizardTest.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);

		assertEquals("Expected no build to be triggered for imported project due to unsetting dialog option checkbox",
				0, ImportExistingProjectsWizardTest.TestBuilder.builderCallCount.get());
	}

	@Test
	public void test22RebuildImportedProjects() throws Exception {
		WizardProjectsImportPage wpip = createImportWizardWithDataLocation(
				"ImportExistingProjectsWizardTestRebuildProject"); // located in data/workspaces/

		wpip.setRebuildProjectsAfterImport(true);

		ImportExistingProjectsWizardTest.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);

		assertEquals("Expected build to be triggered for imported project due to setting dialog option checkbox",
				1, ImportExistingProjectsWizardTest.TestBuilder.builderCallCount.get());
	}

	@Test
	public void test23NoRebuildImportedProjects() throws Exception {
		disableWorkspaceAutoBuild();

		WizardProjectsImportPage wpip = createImportWizardWithDataLocation(
				"ImportExistingProjectsWizardTestRebuildProject"); // located in data/workspaces/

		wpip.setRebuildProjectsAfterImport(false);

		ImportExistingProjectsWizardTest.TestBuilder.resetCallCount();
		wpip.createProjects();
		processEvents();
		Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);

		assertEquals("Expected no build to be triggered for imported project due to unsetting dialog option checkbox",
				0, ImportExistingProjectsWizardTest.TestBuilder.builderCallCount.get());
	}

