		clearView();
		deleteAllProjects();
		shareProjects(repositoryFile);
		refreshAndWait();
		assertProjectExistence(PROJ1, true);
		assertEmpty();

		getOrOpenView().show();

		SWTBotView view = bot.viewById("org.eclipse.jdt.ui.PackageExplorer");
		view.show();
		SWTBotTree explorerTree = view.bot().tree();
		SWTBotTreeItem projectItem = getProjectItem(explorerTree, PROJ1)
				.select();
		ContextMenuHelper.clickContextMenuSync(explorerTree, "Show In",
				viewName);
		refreshAndWait();
		assertHasRepo(repositoryFile);
		SWTBotTree viewerTree = getOrOpenView().bot().tree();

		TableCollection selection = viewerTree.selection();
		assertTrue("Selection should contain one element",
				selection.rowCount() == 1);
		String nodeText = selection.get(0).get(0);
		assertTrue("Node text should contain project name", projectItem
				.getText().startsWith(nodeText));

		view.show();
		projectItem.expand().getNode(FOLDER).expand().getNode(FILE1).select();

		ContextMenuHelper.clickContextMenuSync(explorerTree, "Show In",
				viewName);

		selection = viewerTree.selection();
		assertTrue("Selection should contain one eelement",
				selection.rowCount() == 1);
		nodeText = selection.get(0).get(0);
