		branchNameDialog = bot
				.shell(UIText.BranchSelectionDialog_QuestionNewBranchTitle);
		assertFalse(branchNameDialog.bot().button(IDialogConstants.OK_LABEL)
				.isEnabled());
		branchNameDialog.bot().text().setText("Renamed");
		bot.button(IDialogConstants.OK_LABEL).click();
