		SWTBotPerspective perspective = null;
		try {
			perspective = bot.activePerspective();

			bot.perspectiveById("org.eclipse.pde.ui.PDEPerspective").activate();
			clearView();
			deleteAllProjects();
			shareProjects(repositoryFile);
			refreshAndWait();
			assertProjectExistence(PROJ1, true);
			assertEmpty();

			SWTBotTree explorerTree = bot.viewById(
					"org.eclipse.jdt.ui.PackageExplorer").bot().tree();
			SWTBotTreeItem projectItem = getProjectItem(explorerTree, PROJ1)
					.select();
			ContextMenuHelper.clickContextMenuSync(explorerTree, "Show In",
					viewName);
			refreshAndWait();
			assertHasRepo(repositoryFile);
			SWTBotTree viewerTree = getOrOpenView().bot().tree();

			TableCollection selection = viewerTree.selection();
			assertTrue("Selection should contain one element", selection
					.rowCount() == 1);
			String nodeText = selection.get(0).get(0);
			assertTrue("Node text should contain project name", projectItem
					.getText().startsWith(nodeText));

			projectItem.expand().getNode(FOLDER).expand().getNode(FILE1)
					.select();

			ContextMenuHelper.clickContextMenu(explorerTree, "Show In",
					viewName);

			selection = viewerTree.selection();
			assertTrue("Selection should contain one eelement", selection
					.rowCount() == 1);
			nodeText = selection.get(0).get(0);
