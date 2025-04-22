
	@Test
	public void testRenameBranch() throws Exception {
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

		bot.button(UIText.BranchSelectionDialog_Rename).click();

		branchNameDialog = bot
				.shell(UIText.BranchSelectionDialog_QuestionNewBranchTitle);
		assertFalse(branchNameDialog.bot().button(IDialogConstants.OK_LABEL)
				.isEnabled());
		branchNameDialog.bot().text().setText("Renamed");
		bot.button(IDialogConstants.OK_LABEL).click();

		bot.button(UIText.BranchSelectionDialog_OkCheckout).click();
		TestUtil.joinJobs(JobFamilies.CHECKOUT);
		assertEquals("New Branch should be checked out", "Renamed",
				lookupRepository(repositoryFile).getBranch());
	}

