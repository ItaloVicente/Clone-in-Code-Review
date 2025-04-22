	private void resetRepositoryToCreateInitialTag(String projectName)
			throws Exception {
		showDialog(projectName, "Team", "Reset...");

		bot.shell(ResetCommand_WizardTitle).bot().activeShell();

		SWTBotTreeItem tagsNode = bot.tree().getTreeItem("Tags");
		tagsNode.expand();
		tagsNode.getNode(INITIAL_TAG).select();

		bot.radio(ResetTargetSelectionDialog_ResetTypeHardButton).click();
		bot.button(ResetTargetSelectionDialog_ResetButton).click();

		bot.shell(ResetTargetSelectionDialog_ResetQuestion).bot().activeShell();
		bot.button("Yes").click();
		TestUtil.joinJobs(JobFamilies.RESET);
