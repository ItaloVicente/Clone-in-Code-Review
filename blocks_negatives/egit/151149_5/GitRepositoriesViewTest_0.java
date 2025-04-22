		toggleLinkWithSelection();

		assertTrue(tree.selection().get(0, 0).contains(REPO1));

		projectExplorerTree = TestUtil.getExplorerTree();
		getProjectItem(projectExplorerTree, PROJ1).select();
