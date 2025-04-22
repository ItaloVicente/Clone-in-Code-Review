	@Test
	public void testMergeSquash() throws Exception {
		prepare();
		String oldContent = getTestFileContent();
		RevCommit oldCommit = getCommitForHead();
		createNewBranch("newBranch", true);
		touchAndSubmit("branch commit #1");
		touchAndSubmit("branch commit #2");
		String branchContent = getTestFileContent();
		checkoutBranch(Constants.MASTER);
		assertEquals(oldContent, getTestFileContent());

		mergeBranch("newBranch", true);

		assertEquals(oldCommit, getCommitForHead());
		assertEquals(branchContent, getTestFileContent());
	}

	private RevCommit getCommitForHead() throws Exception {
		Repository repo = lookupRepository(repositoryFile);
		RevWalk rw = new RevWalk(repo);
		ObjectId id = repo.resolve(repo.getFullBranch());
		return rw.parseCommit(id);
	}

	private void mergeBranch(String branchToMerge, boolean squash) throws Exception {
		SWTBotShell mergeDialog = openMergeDialog();
		mergeDialog.bot().tree().getTreeItem(LOCAL_BRANCHES).expand().getNode(branchToMerge).select();
		if (squash)
			mergeDialog.bot().radio(UIText.MergeTargetSelectionDialog_MergeTypeSquashButton).click();
		mergeDialog.bot().button(UIText.MergeTargetSelectionDialog_ButtonMerge).click();
		bot.shell(UIText.MergeAction_MergeResultTitle).close();
	}

	private void createNewBranch(String newBranch, boolean checkout) {
		SWTBotShell newBranchDialog = openCreateBranchDialog();
		newBranchDialog.bot().comboBoxWithId("BaseBranch").setSelection(0);
		newBranchDialog.bot().textWithId("BranchName").setText(newBranch);
		if (!checkout)
			newBranchDialog.bot().checkBox(UIText.CreateBranchPage_CheckoutButton).deselect();
		newBranchDialog.bot().button(IDialogConstants.FINISH_LABEL).click();
	}

