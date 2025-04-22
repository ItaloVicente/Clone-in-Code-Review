		String rootText = labelProvider.getStyledText(root).getString();
		SWTBotTreeItem rootItem = tree.getTreeItem(rootText);
		SWTBotTreeItem branchesItem = TestUtil.expandAndWait(rootItem)
				.getNode(labelProvider.getStyledText(branches).getString());
		SWTBotTreeItem localItem = TestUtil.expandAndWait(branchesItem).getNode(
				labelProvider.getStyledText(localBranches).getString());
