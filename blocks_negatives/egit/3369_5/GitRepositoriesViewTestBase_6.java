			bot.menu("Window").menu("Show View").menu("Other...").click();
			SWTBotShell shell = bot.shell("Show View").activate();
			shell.bot().tree().expandNode(gitCategory).getNode(viewName)
					.select();
			shell.bot().button(IDialogConstants.OK_LABEL).click();
			TestUtil.joinJobs(JobFamilies.REPO_VIEW_REFRESH);

			viewbot = bot.viewByTitle(viewName);

			assertNotNull("Repositories View should not be null", viewbot);
