	@Test
	public void testConfigurationCloseImportedProjects() throws Exception {
		String location = ImportTestUtils.copyDataLocation("ImportExistingProjectsWizardTestRebuildProject");
		File file = new File(location);
		SmartImportWizard wizard = new SmartImportWizard();
		wizard.setInitialImportSource(file);
		proceedSmartImportWizard(wizard, page -> page.setCloseProjectsAfterImport(true));

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

		try {
			assertEquals(2, projects.length);

			IProject rootProject = ResourcesPlugin.getWorkspace().getRoot()
					.getProject("ImportExistingProjectsWizardTestRebuildProject");
			assertTrue("Missing test project", rootProject.exists());
			assertFalse("Test project should be closed after import", rootProject.isAccessible());
		} finally {
			ImportTestUtils.deleteWorkspaceProjects(projects);
		}
	}

	@Test
	public void testConfigurationRebuildImportedProjectsNoAutoBuild() throws Exception {
		ImportTestUtils.disableWorkspaceAutoBuild();

		String location = ImportTestUtils.copyDataLocation("ImportExistingProjectsWizardTestRebuildProject");
		File file = new File(location);
		SmartImportWizard wizard = new SmartImportWizard();
		wizard.setInitialImportSource(file);

		ImportTestUtils.TestBuilder.resetCallCount();
		proceedSmartImportWizard(wizard, page -> {
			page.setCloseProjectsAfterImport(false);
			page.setRebuildProjectsAfterImport(true);
		});
		ImportTestUtils.waitForBuild();

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		try {
			assertEquals(2, projects.length);

			IProject rootProject = ResourcesPlugin.getWorkspace().getRoot()
					.getProject("ImportExistingProjectsWizardTestRebuildProject");

			assertTrue("Missing test project", rootProject.exists());
			ImportTestUtils.TestBuilder.assertCleanFullBuildWasDone();

			ImportTestUtils.assertBinFolderIsCleaned(rootProject);
		} finally {
			ImportTestUtils.deleteWorkspaceProjects(projects);
		}
	}

	@Test
	public void testConfigurationRebuildImportedProjectsAutoBuild() throws Exception {
		String location = ImportTestUtils.copyDataLocation("ImportExistingProjectsWizardTestRebuildProject");
		File file = new File(location);
		SmartImportWizard wizard = new SmartImportWizard();
		wizard.setInitialImportSource(file);

		ImportTestUtils.TestBuilder.resetCallCount();
		proceedSmartImportWizard(wizard, page -> {
			page.setCloseProjectsAfterImport(false);
			page.setRebuildProjectsAfterImport(true);
		});
		ImportTestUtils.waitForBuild();

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		try {
			assertEquals(2, projects.length);

			IProject rootProject = ResourcesPlugin.getWorkspace().getRoot()
					.getProject("ImportExistingProjectsWizardTestRebuildProject");

			assertTrue("Missing test project", rootProject.exists());
			ImportTestUtils.TestBuilder.assertCleanFullBuildWasDone();

			ImportTestUtils.assertBinFolderIsCleaned(rootProject);
		} finally {
			ImportTestUtils.deleteWorkspaceProjects(projects);
		}
	}

