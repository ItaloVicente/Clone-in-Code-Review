		SWTBotShell dialog = openBranchDialog();
		dialog.bot().button(UIText.BranchSelectionDialog_NewBranch).click();
		SWTBotShell branchNameDialog = bot
				.shell(UIText.CreateBranchWizard_NewBranchTitle);
		SWTBotText branchName = bot.textWithId("BranchName");
		branchName.setText("master");
		assertFalse(branchNameDialog.bot().button(IDialogConstants.FINISH_LABEL)
