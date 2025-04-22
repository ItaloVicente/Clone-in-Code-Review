		SWTBotTreeItem remotesNode = myRepoViewUtil.getRemotesItem(tree,
				clonedRepositoryFile);
		SWTBotTreeItem originNode = TestUtil.expandAndWait(remotesNode)
				.getNode("origin");
		if (useRemote) {
			originNode.select();
		} else {
			TestUtil.expandAndWait(originNode).getNode(fetchMode ? 0 : 1)
