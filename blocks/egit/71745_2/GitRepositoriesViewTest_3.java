		SWTBotTreeItem item = myRepoViewUtil.getWorkdirItem(tree,
				repositoryFile);
		for (String node : nodes) {
			item = TestUtil.expandAndWait(item).getNode(node);
		}
		return item.expand();
