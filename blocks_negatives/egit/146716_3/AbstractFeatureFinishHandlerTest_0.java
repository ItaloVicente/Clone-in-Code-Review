	@Override
	protected void createFeature(String featureName) {
		final SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		getProjectItem(projectExplorerTree, PROJ1).select();
		final String[] menuPath = new String[] {
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("TeamGitFlowMenu.name", false, Activator.getDefault().getBundle()),
				util.getPluginLocalizedValue("TeamGitFlowFeatureStart.name", false, Activator.getDefault().getBundle()) };

		ContextMenuHelper.clickContextMenu(projectExplorerTree, menuPath);

		bot.waitUntil(shellIsActive(UIText.FeatureStartHandler_provideFeatureName));
		bot.text().setText(featureName);
		bot.button(UIText.StartDialog_ButtonOK).click();
		bot.waitUntil(Conditions.waitForJobs(JobFamilies.GITFLOW_FAMILY, "Git flow jobs"));
	}

