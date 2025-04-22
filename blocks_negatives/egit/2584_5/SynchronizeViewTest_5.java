		if (waitForProject)
			TestUtil.waitUntilTreeHasNodeWithText(bot, syncViewTree, "> " +
					PROJ1, 10000);
		syncViewTree.getAllItems()[0].expand().getItems()[0].expand()
				.getItems()[0].doubleClick();

		return bot.editorByTitle(FILE1);
