		SWTBotShell dialog = openBranchDialog();
		dialog.bot().button(UIText.BranchSelectionDialog_NewBranch).click();
		SWTBotShell branchNameDialog = bot
				.shell(UIText.CreateBranchWizard_NewBranchTitle);
		SWTBotText branchName = bot.textWithId("BranchName");
		branchName.setText("master");
		assertFalse(branchNameDialog.bot().button(IDialogConstants.FINISH_LABEL)
				.isEnabled());
		branchName.setText("Unrenamed");
		branchNameDialog.bot().checkBox(UIText.CreateBranchPage_CheckoutButton)
				.deselect();
		branchNameDialog.bot().button(IDialogConstants.FINISH_LABEL).click();

		assertEquals("New Branch should be selected", "Unrenamed", bot.tree()
				.selection().get(0, 0));
