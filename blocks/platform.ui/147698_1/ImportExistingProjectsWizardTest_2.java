	private WizardProjectsImportPage createImportWizardWithZipLocation(String testProject) throws Exception {
		zipLocation = ImportTestUtils.copyZipLocation(testProject, testProject); // located in data/workspaces/
		ImportTestUtils.deleteWorkspaceProjects();

		WizardProjectsImportPage wpip = getNewWizard();
		wpip.getProjectFromDirectoryRadio().setSelection(false); // select the other option
		wpip.updateProjectsList(zipLocation);
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

