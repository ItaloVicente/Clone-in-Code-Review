	public void testOpen() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		SWTBotTreeItem item = getRootItem(tree, repositoryFile);
		item.collapse();
		refreshAndWait();
		item = getRootItem(tree, repositoryFile);
		assertEquals("Wrong number of children", 1, item.getNodes().size());
		item.doubleClick();
		assertEquals("Wrong number of children", 5, item.getNodes().size());
		item = getWorkdirItem(tree, repositoryFile).expand();
		SWTBotTreeItem fileiItem = item.getNode(PROJ1).expand().getNode(FOLDER)
				.expand().getNode(FILE1).select();
		fileiItem.doubleClick();
		assertTrue(bot.activeEditor().getTitle().equals(FILE1));
		bot.activeEditor().close();
		item = getLocalBranchesItem(tree, repositoryFile).expand().getNode(
				"master").doubleClick();
		refreshAndWait();
		String contentMaster = getTestFileContent();
		item = getLocalBranchesItem(tree, repositoryFile).expand().getNode(
				"stable").doubleClick();
		refreshAndWait();
		String contentStable = getTestFileContent();
		assertTrue(!contentMaster.equals(contentStable));
