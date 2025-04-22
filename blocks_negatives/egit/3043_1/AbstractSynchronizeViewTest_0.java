		new Eclipse().openPreferencePage(null);
		bot.tree().getTreeItem("Team").expand().select();
		SWTBotRadio syncPerspectiveCheck = bot.radio("Never");
		if (!syncPerspectiveCheck.isSelected())
			syncPerspectiveCheck.click();
		bot.comboBox(0).setSelection("None");

		bot.comboBox().setSelection("None");

		bot.button(IDialogConstants.OK_LABEL).click();
