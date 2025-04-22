	private SWTBot getCompareEditorForFileInGitChangeSet(String fileName) {
		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		syncViewTree.getAllItems()[0].collapse().doubleClick();
		syncViewTree.getAllItems()[0].getItems()[0].getNode(FOLDER)
				.getNode(fileName).doubleClick();

		return bot.editorByTitle(fileName).bot();
	}

	private SWTBotEditor getCompareEditorForFileInWorkspaceModel()
			throws Exception {
		SWTBotTree syncViewTree = setPresentationModel("Workspace").tree();
		syncViewTree.getAllItems()[0].expand().getItems()[0].expand()
				.getItems()[0].doubleClick();

		return bot.editorByTitle(FILE1);
	}

