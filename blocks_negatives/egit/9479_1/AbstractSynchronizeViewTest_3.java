	protected SWTBotEditor getCompareEditorForFileInGitChangeSet(
			String fileName,
			boolean includeLocalChanges) {
		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();

		SWTBotTreeItem rootTree;
		if (includeLocalChanges)
			rootTree = waitForNodeWithText(syncViewTree,
					GitModelWorkingTree_workingTree);
		else
			rootTree = waitForNodeWithText(syncViewTree, TEST_COMMIT_MSG);

		SWTBotTreeItem projNode = waitForNodeWithText(rootTree, PROJ1);
		return getCompareEditor(projNode, fileName);
	}

	protected SWTBotEditor getCompareEditorForNonWorkspaceFileInGitChangeSet(
			final String fileName) {
		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();

		SWTBotTreeItem rootTree = waitForNodeWithText(syncViewTree,
					GitModelWorkingTree_workingTree);
		waitForNodeWithText(rootTree, fileName).doubleClick();

		SWTBotEditor editor = bot
				.editor(new CompareEditorTitleMatcher(fileName));

		return editor;
	}

