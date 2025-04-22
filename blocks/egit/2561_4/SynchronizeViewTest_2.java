	private SWTBot getCompareEditorForFileInGitChangeSet(String fileName,
			boolean waitForWorkingTree) {
		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		if (waitForWorkingTree)
			syncViewTree.getTreeItem(UIText.GitModelWorkingTree_workingTree);
		syncViewTree.getAllItems()[0].collapse().doubleClick();
		syncViewTree.getAllItems()[0].getItems()[0].getNode(FOLDER)
				.getNode(fileName).doubleClick();

		return bot.editorByTitle(fileName).bot();
	}

	private SWTBotEditor getCompareEditorForFileInWorkspaceModel(
			boolean waitForProject)
			throws Exception {
		SWTBotTree syncViewTree = setPresentationModel("Workspace").tree();
		if (waitForProject)
			TestUtil.waitUntilTreeHasNodeWithText(bot, syncViewTree, "> " +
					PROJ1, 10000);
		syncViewTree.getAllItems()[0].expand().getItems()[0].expand()
				.getItems()[0].doubleClick();

		return bot.editorByTitle(FILE1);
	}

