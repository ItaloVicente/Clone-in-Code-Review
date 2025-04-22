	@Test
	public void testCreateBranchSelectSource() throws Exception {
		Repository repo = lookupRepository(repositoryFile);
		assertNull(repo.resolve("branch-from-tag"));

		SWTBotShell createBranchDialog = openCreateBranchDialog();
		createBranchDialog.bot()
				.button(UIText.CreateBranchPage_SourceSelectButton).click();

		SWTBotShell sourceSelectionDialog = bot
				.shell(UIText.CreateBranchPage_SourceSelectionDialogTitle);
		SWTBotTree tree = sourceSelectionDialog.bot().tree();
		SWTBotTreeItem tags = tree
				.expandNode(UIText.RepositoriesViewLabelProvider_TagsNodeText);
		TestUtil.getChildNode(tags, "SomeTag").select();
		sourceSelectionDialog.bot().button(IDialogConstants.OK_LABEL).click();

		SWTBotLabel sourceLabel = createBranchDialog.bot().label(3);
		assertEquals("SomeTag", sourceLabel.getText());

		createBranchDialog.bot().textWithId("BranchName")
				.setText("branch-from-tag");
		createBranchDialog.bot()
				.checkBox(UIText.CreateBranchPage_CheckoutButton).deselect();
		createBranchDialog.bot().button(IDialogConstants.FINISH_LABEL).click();

		ObjectId resolvedBranch = repo.resolve("branch-from-tag");
		ObjectId resolvedTagCommit = repo.resolve("SomeTag^{commit}");
		assertNotNull(resolvedBranch);
		assertEquals(resolvedTagCommit, resolvedBranch);
	}

