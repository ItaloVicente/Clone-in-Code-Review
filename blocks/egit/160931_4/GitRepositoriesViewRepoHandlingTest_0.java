	@Test
	public void testCloneWithViewClosed() throws Exception {
		clearView();
		refreshAndWait();
		assertEmpty();
		getOrOpenView().close();
		UIThreadRunnable.asyncExec(() -> {
			CommonUtils.runCommand("org.eclipse.egit.ui.RepositoriesViewClone",
					null);
		});
		SWTBotShell shell = bot.shell(UIText.GitCloneWizard_title);
		shell.bot().tree().select("Clone URI");

		shell.bot().button("Next >").click(); // for some reason, textWithLabel
		shell.bot().textInGroup(UIText.RepositorySelectionPage_groupLocation, 0)
				.setText(repositoryFile.getPath());
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		bot.waitUntil(widgetIsEnabled(shell.bot().tree()), 60000);
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		waitInUI();
		SWTBotText pathText = shell.bot().text(0);
		pathText.setText(pathText.getText() + "Cloned");
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
		TestUtil.joinJobs(JobFamilies.CLONE);
		refreshAndWait();
		assertHasClonedRepo();
	}

