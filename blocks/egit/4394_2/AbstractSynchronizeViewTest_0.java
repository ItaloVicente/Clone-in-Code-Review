	protected SWTBotEditor getCompareEditorForNonWorkspaceFileInGitChangeSet(
			final String fileName) {
		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();

		SWTBotTreeItem rootTree = waitForNodeWithText(syncViewTree,
					GitModelWorkingTree_workingTree);
		waitForNodeWithText(rootTree, fileName).doubleClick();

		SWTBotEditor editor = bot
				.editor(new ComapreEditorTitleMatcher(fileName));

		return editor;
	}

