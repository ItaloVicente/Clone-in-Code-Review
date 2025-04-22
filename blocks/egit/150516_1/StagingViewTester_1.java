	public void compareWithIndex(String path) {
		SWTBotTree unstagedTree = stagingView.bot().tree(0);

		TestUtil.waitUntilTreeHasNodeContainsText(stagingView.bot(),
				unstagedTree, path, 10000);

		TestUtil.getNode(unstagedTree.getAllItems(), path).select();

		ContextMenuHelper.clickContextMenuSync(unstagedTree,
				UIText.StagingView_CompareWithIndexMenuLabel);
	}

