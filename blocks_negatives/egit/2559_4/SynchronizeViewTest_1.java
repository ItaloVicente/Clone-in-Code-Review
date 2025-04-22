		bot.shell("Synchronize repository: " + REPO1 + File.separator + ".git")
				.activate();

		bot.checkBox(
				UIText.SelectSynchronizeResourceDialog_includeUncommitedChanges)
				.click();

		bot.comboBox(0)
				.setSelection(UIText.SynchronizeWithAction_localRepoName);
		bot.comboBox(1).setSelection(HEAD);

		bot.comboBox(2)
				.setSelection(UIText.SynchronizeWithAction_localRepoName);
		bot.comboBox(3).setSelection(MASTER);

		bot.button(IDialogConstants.OK_LABEL).click();
