		SWTBotPerspective perspective = null;
		try {
			perspective = bot.activePerspective();
			SWTBotTree tree = getOrOpenView().bot().tree();
			getRootItem(tree, repositoryFile).select();
			assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

			SWTBotTree projectExplorerTree = bot.viewById(
					"org.eclipse.jdt.ui.PackageExplorer").bot().tree();
			projectExplorerTree.getAllItems()[0].select();

			assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

			getOrOpenView().toolbarButton(
					myUtil.getPluginLocalizedValue("LinkWithSelectionCommand"))
					.click();

			assertTrue(tree.selection().get(0, 0).startsWith(REPO1));

			projectExplorerTree = bot.viewById(
					"org.eclipse.jdt.ui.PackageExplorer").bot().tree();
			projectExplorerTree.getAllItems()[0].select();

			assertTrue(tree.selection().get(0, 0).equals(PROJ1));

			getOrOpenView().toolbarButton(
					myUtil.getPluginLocalizedValue("LinkWithSelectionCommand"))
					.click();

		} finally {
			if (perspective != null)
				perspective.activate();
		}
