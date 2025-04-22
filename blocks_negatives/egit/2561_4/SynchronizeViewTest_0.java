		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		syncViewTree.getTreeItem(UIText.GitModelWorkingTree_workingTree);
		syncViewTree.getAllItems()[0].collapse().doubleClick();
		syncViewTree.getAllItems()[0].getItems()[0].getNode(FOLDER)
				.getNode(FILE1).doubleClick();

