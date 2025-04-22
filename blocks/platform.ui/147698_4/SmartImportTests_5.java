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
			assertFalse("Test project should be closed after import", rootProject.isOpen());
		} finally {
			ImportTestUtils.deleteWorkspaceProjects(projects);
		}
	}

	@Test
	public void testConfigurationFullBuildAfterImportedProjects() throws Exception {
		String location = ImportTestUtils.copyDataLocation("ImportExistingProjectsWizardTestRebuildProject");
		File file = new File(location);
		SmartImportWizard wizard = new SmartImportWizard();
		wizard.setInitialImportSource(file);

		ImportTestUtils.TestBuilder.resetCallCount();
		proceedSmartImportWizard(wizard, page -> {
			page.setCloseProjectsAfterImport(false);
		});
		ImportTestUtils.waitForBuild();

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		try {
			assertEquals(2, projects.length);

			IProject rootProject = ResourcesPlugin.getWorkspace().getRoot()
					.getProject("ImportExistingProjectsWizardTestRebuildProject");

			assertTrue("Missing test project", rootProject.exists());
			ImportTestUtils.TestBuilder.assertFullBuildWasDone();
		} finally {
			ImportTestUtils.deleteWorkspaceProjects(projects);
		}
	}

