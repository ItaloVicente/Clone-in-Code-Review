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

			SWTBotTree projectExplorerTree = bot.viewById(
					"org.eclipse.ui.navigator.ProjectExplorer").bot().tree();
			getProjectItem(projectExplorerTree, PROJ1).select();

			assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

			getOrOpenView().toolbarButton(
					myUtil.getPluginLocalizedValue("LinkWithSelectionCommand"))
					.click();

			assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

			projectExplorerTree = bot.viewById(
					"org.eclipse.ui.navigator.ProjectExplorer").bot().tree();
			getProjectItem(projectExplorerTree, PROJ1).select();

			assertTrue(tree.selection().get(0, 0).equals(PROJ1));

			getOrOpenView().toolbarButton(
					myUtil.getPluginLocalizedValue("LinkWithSelectionCommand"))
					.click();

		} finally {
			if (perspective != null)
				perspective.activate();
		}
