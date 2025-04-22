	private void finishFeature() {
		final SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		getProjectItem(projectExplorerTree, PROJ1).select();
		final String[] menuPath = new String[] {
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("TeamGitFlowMenu.name", false, Activator.getDefault().getBundle()),
				util.getPluginLocalizedValue("TeamGitFlowFeatureFinish.name", false, Activator.getDefault().getBundle()) };
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				ContextMenuHelper.clickContextMenuSync(projectExplorerTree, menuPath);
			}
		});
		bot.checkBox(UIText.FinishFeatureDialog_keepBranch).click();
		bot.button("OK").click();
		bot.waitUntil(Conditions.waitForJobs(JobFamilies.GITFLOW_FAMILY, "Git flow jobs"));
	}

	private void init() throws CoreException {
		new InitOperation(repository).execute(null);
	}

