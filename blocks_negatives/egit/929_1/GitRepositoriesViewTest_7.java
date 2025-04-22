	public void testAddRepoButton() throws Exception {
		getOrOpenView().toolbarButton(
				myUtil.getPluginLocalizedValue("AddRepositoryCommand")).click();
		SWTBotShell shell = bot.shell(
				UIText.RepositorySearchDialog_AddGitRepositories).activate();
		shell.close();
