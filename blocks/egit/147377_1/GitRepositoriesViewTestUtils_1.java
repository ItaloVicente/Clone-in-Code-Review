	public SWTBotTreeItem getStashesItem(SWTBotTree tree, File repositoryFile)
			throws Exception {
		Repository repository = lookupRepository(repositoryFile);
		RepositoryNode root = new RepositoryNode(null, repository);
		StashNode stashes = new StashNode(root, repository);

		String rootText = labelProvider.getText(root);
		String stashesText = labelProvider.getText(stashes);
		SWTBotTreeItem stashesItem = TestUtil.navigateTo(tree, rootText,
				stashesText);
		return stashesItem;
	}

