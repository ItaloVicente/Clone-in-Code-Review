		assertEquals("New Branch should be selected", "Unrenamed", bot.tree()
				.selection().get(0, 0));

		bot.button(UIText.BranchSelectionDialog_Rename).click();

		branchNameDialog = bot
				.shell(UIText.BranchSelectionDialog_QuestionNewBranchTitle);
		assertFalse(branchNameDialog.bot().button(IDialogConstants.OK_LABEL)
				.isEnabled());
		branchNameDialog.bot().text().setText("Renamed");
		bot.button(IDialogConstants.OK_LABEL).click();

		bot.button(UIText.BranchSelectionDialog_OkCheckout).click();
