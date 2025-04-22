		SWTBotTree tree = getOrOpenView().bot().tree();
		myRepoViewUtil.getRootItem(tree, repositoryFile).select();
		assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

		SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		getProjectItem(projectExplorerTree, PROJ1).select();

		assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

		toggleLinkWithSelection();

		assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

		projectExplorerTree = TestUtil.getExplorerTree();
		getProjectItem(projectExplorerTree, PROJ1).select();

		assertTrue(tree.selection().get(0, 0).equals(PROJ1));

		toggleLinkWithSelection();
