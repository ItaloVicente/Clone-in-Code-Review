	protected SWTBotEditor getCompareEditorForFileInWorkspaceModel(
			String fileName) {
		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();

		SWTBotTreeItem projNode = waitForNodeWithText(syncViewTree, PROJ1);
		SWTBotEditor editor = getCompareEditor(projNode, fileName);

		return editor;
	}

