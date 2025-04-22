	}

	private void clickInit() {
		final SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		final String[] menuPath = new String[] {
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("TeamGitFlowInit.name", false, Activator.getDefault().getBundle()) };
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				ContextMenuHelper.clickContextMenuSync(projectExplorerTree,
						menuPath);
			}
		});
	}

	private TestRepository createAndShare(String projectName) throws Exception {
		IProgressMonitor progressMonitor = new NullProgressMonitor();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);
		project.create(progressMonitor);
		project.open(progressMonitor);
		TestUtil.waitForJobs(50, 5000);

		File gitDir = new File(project.getProject().getLocationURI().getPath(), Constants.DOT_GIT);
		TestRepository testRepository = new TestRepository(gitDir);
		testRepository.connect(project.getProject());
		TestUtil.waitForJobs(50, 5000);

		return testRepository;
