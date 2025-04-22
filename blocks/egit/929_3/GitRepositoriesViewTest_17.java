	public void testLinkWithEditor() throws Exception {
		deleteAllProjects();
		shareProjects(repositoryFile);
		SWTBotPerspective perspective = null;
		try {
			perspective = bot.activePerspective();
			bot.perspectiveById("org.eclipse.ui.resourcePerspective")
					.activate();
			SWTBotTree tree = getOrOpenView().bot().tree();
			getRootItem(tree, repositoryFile).select();
			assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

			SWTBotView view = bot
					.viewById("org.eclipse.ui.navigator.ProjectExplorer");
			SWTBotTree projectExplorerTree = view.bot().tree();

			SWTBotTreeItem item = getProjectItem(projectExplorerTree, PROJ1)
					.expand().getNode(FOLDER).expand().getNode(FILE1);
			view.show();
			item.doubleClick();

			item = getProjectItem(projectExplorerTree, PROJ1).expand().getNode(
					FOLDER).expand().getNode(FILE2);
			view.show();
			item.doubleClick();

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
			waitInUI();
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
