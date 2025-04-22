	public void testLinkWithEditor() throws Exception {
		SWTBotPerspective perspective = null;
		try {
			perspective = bot.activePerspective();
			SWTBotTree tree = getOrOpenView().bot().tree();
			getRootItem(tree, repositoryFile).select();
			assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

			SWTBotTree projectExplorerTree = bot.viewById(
					"org.eclipse.jdt.ui.PackageExplorer").bot().tree();

			projectExplorerTree.getAllItems()[0].expand().getNode(FOLDER)
					.expand().getNode(FILE1).doubleClick();
			projectExplorerTree.getAllItems()[0].expand().getNode(FOLDER)
					.expand().getNode(FILE2).doubleClick();

			assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

			getOrOpenView().toolbarButton("Link with Editor").click();
			bot.editorByTitle(FILE2).show();
			waitInUI();
			assertTrue(tree.selection().get(0, 0).equals(FILE2));

			bot.editorByTitle(FILE1).show();
			waitInUI();
			assertTrue(tree.selection().get(0, 0).equals(FILE1));

			getOrOpenView().toolbarButton("Link with Editor").click();

			bot.editorByTitle(FILE2).show();
			waitInUI();
			assertTrue(tree.selection().get(0, 0).equals(FILE1));

			bot.editorByTitle(FILE1).show();

			getWorkdirItem(tree, repositoryFile).expand().getNode(PROJ1)
					.expand().getNode(FOLDER).expand().getNode(FILE2).select();

			assertEquals(FILE1, bot.activeEditor().getTitle());

			getOrOpenView().toolbarButton("Link with Editor").click();

			getWorkdirItem(tree, repositoryFile).expand().getNode(PROJ1)
					.expand().getNode(FOLDER).expand().getNode(FILE2).select();
			waitInUI();
			assertEquals(FILE2, bot.activeEditor().getTitle());

			getWorkdirItem(tree, repositoryFile).expand().getNode(PROJ1)
					.expand().getNode(FOLDER).expand().getNode(FILE1).select();
			waitInUI();
			assertEquals(FILE1, bot.activeEditor().getTitle());

			getOrOpenView().toolbarButton("Link with Editor").click();

			getWorkdirItem(tree, repositoryFile).expand().getNode(PROJ1)
					.expand().getNode(FOLDER).expand().getNode(FILE2).select();
			waitInUI();
			assertEquals(FILE1, bot.activeEditor().getTitle());

		} finally {
			if (perspective != null)
				perspective.activate();
		}
