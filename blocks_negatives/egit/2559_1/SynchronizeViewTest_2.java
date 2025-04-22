		bot.shell("Synchronize repository: " + REPO1 + File.separator + ".git")
				.activate();

		bot.checkBox(
				UIText.SelectSynchronizeResourceDialog_includeUncommitedChanges)
				.click();

		bot.comboBox(0)
				.setSelection(UIText.SynchronizeWithAction_tagsName);
		bot.comboBox(1).setSelection("v0.0");

		bot.comboBox(2)
				.setSelection(UIText.SynchronizeWithAction_localRepoName);
		bot.comboBox(3).setSelection(HEAD);

		bot.button(IDialogConstants.OK_LABEL).click();
