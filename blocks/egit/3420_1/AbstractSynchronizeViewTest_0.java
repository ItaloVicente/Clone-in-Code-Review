	private SWTBotEditor getCompareEditor(SWTBotTreeItem projectNode,
			String fileName) {
		SWTBotTreeItem folderNode = waitForNodeWithText(projectNode, FOLDER);
		waitForNodeWithText(folderNode, fileName).doubleClick();

		SWTBotEditor editor = bot.editorByTitle(fileName);
		SWTBotStyledText styledText = editor.toTextEditor().getStyledText();
		bot.waitUntil(Conditions.widgetIsEnabled(styledText));
		return editor;
	}

