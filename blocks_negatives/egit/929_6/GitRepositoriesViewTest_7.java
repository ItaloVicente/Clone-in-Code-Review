	public void testCloneRepoButton() throws Exception {
		getOrOpenView().toolbarButton(
				myUtil.getPluginLocalizedValue("CloneRepositoryCommand"))
				.click();
		SWTBotShell shell = bot.shell(UIText.GitCloneWizard_title).activate();
		shell.close();
