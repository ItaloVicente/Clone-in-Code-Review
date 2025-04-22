	public void testCreateDeleteBranch() throws Exception {
		assertNull(lookupRepository(repositoryFile).resolve("newBranch"));
		SWTBotShell dialog = openCreateBranchDialog();
		dialog.bot().tree().getTreeItem(LOCAL_BRANCHES).expand().getNode("master").select();
		dialog.bot().button(IDialogConstants.OK_LABEL).click();
		
		SWTBotShell newBranchDialog = bot.shell(UIText.CreateBranchWizard_NewBranchTitle);
		newBranchDialog.bot().textWithId("BranchName").setText("newBranch");
		newBranchDialog.bot().checkBox(UIText.CreateBranchPage_CheckoutButton).deselect();
		newBranchDialog.bot().button(IDialogConstants.FINISH_LABEL).click();
		
		TestUtil.joinJobs(JobFamilies.CHECKOUT);
		assertNotNull(lookupRepository(repositoryFile).resolve("newBranch"));
		
		SWTBotShell deleteBranchDialog = openDeleteBranchDialog();
		deleteBranchDialog.bot().tree().getTreeItem(LOCAL_BRANCHES).expand().getNode("newBranch").select();
		deleteBranchDialog.bot().button(IDialogConstants.OK_LABEL).click();
		
