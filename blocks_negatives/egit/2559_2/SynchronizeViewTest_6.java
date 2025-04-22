		bot.shell("Synchronize repository: " + REPO1 + File.separator + ".git")
				.activate();

		bot.comboBox(2)
				.setSelection(UIText.SynchronizeWithAction_tagsName);
		bot.comboBox(3).setSelection("base");

		bot.button(IDialogConstants.OK_LABEL).click();
