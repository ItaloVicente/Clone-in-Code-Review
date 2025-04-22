	@Test
	public void testDecoration() throws Exception {
		SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		SWTBotTreeItem node = TestUtil.navigateTo(projectExplorerTree,
				childFolder.getFullPath().segments());
		waitForDecorations();
		assertTrue("Folder should have repo/branch decoration",
				node.getText().contains("[master"));
		node = TestUtil.getChildNode(node.expand(), CHILDPROJECT);
		waitForDecorations();
		assertFalse("Folder should not have repo/branch decoration",
				node.getText().contains("["));
		node = TestUtil.navigateTo(projectExplorerTree, CHILDPROJECT);
		waitForDecorations();
		assertTrue("Project should have subrepo/branch decoration",
				node.getText().contains("[child"));
	}

