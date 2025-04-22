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

