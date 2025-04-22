	private SWTBotView getOrOpenView() throws Exception {
		if (viewbot == null) {
			bot.menu("Window").menu("Show View").menu("Other...").click();
			SWTBotShell shell = bot.shell("Show View").activate();
			shell.bot().tree().expandNode(gitCategory).getNode(viewName)
					.select();
			shell.bot().button(0).click();

			viewbot = bot.viewByTitle(viewName);

			assertNotNull("Repositories View should not be null", viewbot);
		}
		return viewbot;
