		bot.comboBox(2)
				.setSelection(UIText.SynchronizeWithAction_tagsName);
		bot.comboBox(3).setSelection("compare2");

		bot.button(IDialogConstants.OK_LABEL).click();

		SWTBotView syncView = bot.viewByTitle("Synchronize");
		syncView.toolbarDropDownButton("Show File System Resources").click()
				.menuItem("Workspace").click();
		SWTBotTree syncViewTree = syncView.bot().tree();
