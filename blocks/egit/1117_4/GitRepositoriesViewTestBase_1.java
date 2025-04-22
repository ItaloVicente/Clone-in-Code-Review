	protected SWTBotTreeItem getTagsItem(SWTBotTree tree, File repositoryFile)
			throws Exception {
		Repository repository = lookupRepository(repositoryFile);
		RepositoryNode root = new RepositoryNode(null, repository);
		TagsNode tags = new TagsNode(root, repository);

		String rootText = labelProvider.getText(root);
		SWTBotTreeItem rootItem = tree.getTreeItem(rootText);
		SWTBotTreeItem tagsItem = rootItem.expand().getNode(
				labelProvider.getText(tags));
		return tagsItem;
	}

