	@Test
	public void test19RebuildImportedProjects() throws Exception {
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

		wpip.getProjectFromDirectoryRadio().setSelection((false)); // select the other option
		wpip.getRebuildProjectsCheckbox().setSelection(true);
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

		ImportExistingProjectsWizardTest.TestBuilder.builderCalled.set(false);
		wpip.createProjects();
		waitForJobs(0, 250);

		assertTrue("Expected build to be triggered for imported project due to setting dialog option checkbox",
				ImportExistingProjectsWizardTest.TestBuilder.builderCalled.get());
	}

