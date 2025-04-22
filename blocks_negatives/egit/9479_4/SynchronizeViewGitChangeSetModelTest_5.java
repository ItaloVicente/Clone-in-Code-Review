		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		expandWorkingTreeNode(syncViewTree);
		assertEquals(2, syncViewTree.getAllItems().length);
		SWTBotTreeItem proj1Node = syncViewTree.getAllItems()[0];
		proj1Node.getItems()[0].expand();
		assertEquals(1, proj1Node.getItems()[0].getItems().length);
		assertEquals(".gitignore",
				proj1Node.getItems()[0].getItems()[0].getText());
