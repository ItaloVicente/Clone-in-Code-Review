			SWTBotTree projectExplorerTree = bot.viewById(
					"org.eclipse.jdt.ui.PackageExplorer").bot().tree();
			SWTBotTreeItem projectItem = getProjectItem(projectExplorerTree, PROJ1).select();
			waitInUI();
			assertTrue("Wrong project label decoration", projectItem.getText()
					.contains("refs"));
