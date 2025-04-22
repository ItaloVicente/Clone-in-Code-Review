	protected SWTBotEditor getCompareEditorForFileInWorkspaceModel()
			throws Exception {
		SWTBotTree syncViewTree = setPresentationModel("Workspace").tree();
		SWTBotTreeItem projectTree = waitForNodeWithText(syncViewTree, PROJ1);
		return getCompareEditor(projectTree, FILE1);
	}

	protected SWTBotEditor getCompareEditorForFileInGitChangeSetModel()
			throws Exception {
		SWTBotTree syncViewTree = setPresentationModel("Git Commits")
				.tree();
		SWTBotTreeItem commitNode = syncViewTree.getAllItems()[0];
		commitNode.expand();
		SWTBotTreeItem projectTree = waitForNodeWithText(commitNode, PROJ1);
		return getCompareEditor(projectTree, FILE1);
	}

	protected SWTBotEditor getCompareEditorForFileInWorspaceModel(
			String fileName) {
		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();

		SWTBotTreeItem projNode = waitForNodeWithText(syncViewTree, PROJ1);
		SWTBotEditor editor = getCompareEditor(projNode, fileName);

		return editor;
	}

