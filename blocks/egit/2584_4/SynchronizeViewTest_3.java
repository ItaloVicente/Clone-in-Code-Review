		SWTBotTreeItem rootTree;
		if (includeLocalChanges)
			rootTree = waitForNodeWithText(syncViewTree,
					GitModelWorkingTree_workingTree);
		else
			rootTree = waitForNodeWithText(syncViewTree, TEST_COMMIT_MSG);

		SWTBotTreeItem projNode = waitForNodeWithText(rootTree, PROJ1);
		SWTBotTreeItem folderNode = waitForNodeWithText(projNode, FOLDER);
		waitForNodeWithText(folderNode, fileName).doubleClick();

		SWTBotEditor editor = bot.editorByTitle(fileName);
		editor.toTextEditor().setFocus();

		return editor;
	}

	private SWTBotTreeItem waitForNodeWithText(SWTBotTree tree, String name) {
		waitUntilTreeHasNodeContainsText(bot, tree, name, 10000);
		return getTreeItemContainingText(tree.getAllItems(), name).expand();
	}

	private SWTBotTreeItem waitForNodeWithText(SWTBotTreeItem tree, String name) {
		waitUntilTreeHasNodeContainsText(bot, tree, name, 10000);
		return getTreeItemContainingText(tree.getItems(), name).expand();
