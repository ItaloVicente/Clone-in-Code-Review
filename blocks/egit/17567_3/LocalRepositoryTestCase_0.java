		org.eclipse.egit.ui.Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.DEFAULT_REPO_DIR, repoRoot.getPath());
	}

	@After
	public void resetWorkspace() throws Exception {
		new Eclipse().reset();
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot()
				.getProjects())
			project.delete(false, false, null);
		shutDownRepositories();
	}

	@BeforeClass
	public static void beforeClassBase() throws Exception {
