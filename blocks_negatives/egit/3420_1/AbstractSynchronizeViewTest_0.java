		SWTBotTreeItem folderNode = waitForNodeWithText(projNode, FOLDER);
		waitForNodeWithText(folderNode, fileName).doubleClick();

		SWTBotEditor editor = bot.editorByTitle(fileName);
		editor.toTextEditor().setFocus();
		return editor;
