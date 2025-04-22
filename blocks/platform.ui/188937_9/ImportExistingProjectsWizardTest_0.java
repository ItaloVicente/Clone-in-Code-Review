	@Test
	public void test23DoNotShowProjectwithSameNameForZipImport() {
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

			IProject[] workspaceProjects = root.getProjects();
			for (IProject workspaceProject : workspaceProjects) {
				FileUtil.deleteProject(workspaceProject);
			}

			FileUtil.createProject("HelloWorld");

			URL archiveFile = FileLocator.toFileURL(FileLocator.find(TestPlugin.getDefault().getBundle(),
					new Path(DATA_PATH_PREFIX + ARCHIVE_HELLOWORLD + ".zip"), null));
			WizardProjectsImportPage wpip = getNewWizard();
			wpip.getProjectFromDirectoryRadio().setSelection((false));
			wpip.updateProjectsList(archiveFile.getPath());

			ProjectRecord[] selectedProjects = wpip.getProjectRecords();
			for (ProjectRecord selectedProject : selectedProjects) {
				if (selectedProject.getProjectName().equals("HelloWorld")) {
					assertTrue(selectedProject.hasConflicts());
				}
			}
		} catch (IOException | CoreException e) {
			fail(e.toString());
		}

	}
