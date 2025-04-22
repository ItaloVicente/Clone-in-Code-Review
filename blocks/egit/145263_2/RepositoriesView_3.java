		return recursiveRepositoryChildNode(cp, null, repository, type);
	}

	private RepositoryTreeNode recursiveRepositoryChildNode(
			ITreeContentProvider cp, RepositoryTreeNode root,
			Repository repository, RepositoryTreeNodeType type) {
		for (Object repo : root == null
				? cp.getElements(getCommonViewer().getInput())
				: cp.getChildren(root)) {
