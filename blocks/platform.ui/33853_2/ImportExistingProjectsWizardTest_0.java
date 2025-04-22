
	public void testGetProjectRecordsShouldIgnoreCorruptArchives()
			throws Exception {

		URL projectsArchive = Platform.asLocalURL(Platform.find(TestPlugin
				.getDefault().getBundle(), new Path(DATA_PATH_PREFIX
				+ CORRUPT_PROJECTS_ARCHIVE + ".zip")));

		WizardProjectsImportPage newWizard = getNewWizard();
		newWizard.getProjectFromDirectoryRadio().setSelection(false);
		newWizard.updateProjectsList(projectsArchive.getPath());

		ProjectRecord[] projectRecords = newWizard.getProjectRecords();

		assertEquals("Should only find the valid project and ignore the corrupt ones", 1, projectRecords.length);
		assertTrue("Expected to find the valid project",
				"Project1".equals(projectRecords[0].getProjectName()));

	}

