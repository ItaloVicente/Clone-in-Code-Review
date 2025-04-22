	protected SWTBotTreeItem getChangeSetTreeItem() {
		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem changeSetItem = waitForNodeWithText(syncViewTree,
				UIText.GitChangeSetModelProviderLabel);
		return changeSetItem;
	}

	protected SWTBotEditor getCompareEditorForFileInGitChangeSet(
			String fileName,
			boolean includeLocalChanges) {
		SWTBotTreeItem changeSetTreeItem = getChangeSetTreeItem();

		SWTBotTreeItem rootTree;
		if (includeLocalChanges)
			rootTree = waitForNodeWithText(changeSetTreeItem,
					GitModelWorkingTree_workingTree);
		else
			rootTree = waitForNodeWithText(changeSetTreeItem, TEST_COMMIT_MSG);

		SWTBotTreeItem projNode = waitForNodeWithText(rootTree, PROJ1);
		return getCompareEditor(projNode, fileName);
	}

	protected SWTBotEditor getCompareEditorForNonWorkspaceFileInGitChangeSet(
			final String fileName) {
		SWTBotTreeItem changeSetTreeItem = getChangeSetTreeItem();

		SWTBotTreeItem rootTree = waitForNodeWithText(changeSetTreeItem,
					GitModelWorkingTree_workingTree);
		waitForNodeWithText(rootTree, fileName).doubleClick();

		SWTBotEditor editor = bot
				.editor(new CompareEditorTitleMatcher(fileName));

		return editor;
	}

	private SWTBotTreeItem getExpandedWorkingTreeItem() {
		SWTBotTreeItem changeSetTreeItem = getChangeSetTreeItem();
		String workingTreeNodeNameString = getWorkingTreeNodeName(changeSetTreeItem);
		SWTBotTreeItem node = changeSetTreeItem.getNode(workingTreeNodeNameString);
		return node.doubleClick();
