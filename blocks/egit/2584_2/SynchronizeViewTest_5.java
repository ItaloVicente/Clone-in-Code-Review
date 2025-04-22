		SWTBotTreeItem projectTree = waitForNodeWithText(syncViewTree, PROJ1);
		SWTBotTreeItem folderTree = waitForNodeWithText(projectTree, FOLDER);
		waitForNodeWithText(folderTree, FILE1).doubleClick();

		SWTBotEditor editor = bot.editorByTitle(FILE1);
		editor.toTextEditor().setFocus();

		return editor;
