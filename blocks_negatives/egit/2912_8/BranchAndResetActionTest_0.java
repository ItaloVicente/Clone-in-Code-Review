	public void testCreateBranch() throws Exception {
		SWTBotShell dialog = openBranchDialog();
		dialog.bot().button(UIText.BranchSelectionDialog_NewBranch).click();
		SWTBotShell branchNameDialog = bot
				.shell(UIText.CreateBranchWizard_NewBranchTitle);
		SWTBotText branchName = bot.textWithId("BranchName");
		branchName.setText("master");
		assertFalse(branchNameDialog.bot().button(IDialogConstants.FINISH_LABEL)
				.isEnabled());
		branchName.setText("NewBranch");
		branchNameDialog.bot().checkBox(UIText.CreateBranchPage_CheckoutButton)
				.deselect();
		branchNameDialog.bot().button(IDialogConstants.FINISH_LABEL).click();

		assertEquals("New Branch should be selected", "NewBranch", bot.tree()
				.selection().get(0, 0));
		bot.button(UIText.BranchSelectionDialog_OkCheckout).click();
