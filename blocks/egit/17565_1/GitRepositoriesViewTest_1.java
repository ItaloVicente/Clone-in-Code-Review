		SWTBotTree tree = getOrOpenView().bot().tree();
		myRepoViewUtil.getRootItem(tree, repositoryFile).select();
		assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

		SWTBotView view = TestUtil.showExplorerView();
		SWTBotTree projectExplorerTree = view.bot().tree();

		SWTBotTreeItem item = getProjectItem(projectExplorerTree, PROJ1)
				.expand().getNode(FOLDER).expand().getNode(FILE1);
		view.show();
		item.doubleClick();

		item = getProjectItem(projectExplorerTree, PROJ1).expand()
				.getNode(FOLDER).expand().getNode(FILE2);
		view.show();
		item.doubleClick();

		assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

		toggleLinkWithSelection();

		bot.editorByTitle(FILE2).show();
		TestUtil.waitUntilTreeHasSelectedNodeWithText(bot, tree, FILE2, 10000);

		bot.editorByTitle(FILE1).show();
		TestUtil.waitUntilTreeHasSelectedNodeWithText(bot, tree, FILE1, 10000);

		toggleLinkWithSelection();

		bot.editorByTitle(FILE2).show();
		TestUtil.waitUntilTreeHasSelectedNodeWithText(bot, tree, FILE1, 10000);

		bot.editorByTitle(FILE1).show();

		myRepoViewUtil.getWorkdirItem(tree, repositoryFile).expand()
				.getNode(PROJ1).expand().getNode(FOLDER).expand()
				.getNode(FILE2).select();

		assertEquals(FILE1, bot.activeEditor().getTitle());

		toggleLinkWithSelection();

		waitInUI();
		myRepoViewUtil.getWorkdirItem(tree, repositoryFile).expand()
				.getNode(PROJ1).expand().getNode(FOLDER).expand()
				.getNode(FILE2).select();
		TestUtil.waitUntilEditorIsActive(bot, bot.editorByTitle(FILE2), 10000);

		myRepoViewUtil.getWorkdirItem(tree, repositoryFile).expand()
				.getNode(PROJ1).expand().getNode(FOLDER).expand()
				.getNode(FILE1).select();
		TestUtil.waitUntilEditorIsActive(bot, bot.editorByTitle(FILE1), 10000);

		toggleLinkWithSelection();

		myRepoViewUtil.getWorkdirItem(tree, repositoryFile).expand()
				.getNode(PROJ1).expand().getNode(FOLDER).expand()
				.getNode(FILE2).select();
		TestUtil.waitUntilEditorIsActive(bot, bot.editorByTitle(FILE1), 10000);
