		return recursiveRepositoryChildNode(cp,
				cp.getElements(getCommonViewer().getInput()), repository, type);
	}

	private RepositoryTreeNode recursiveRepositoryChildNode(
			ITreeContentProvider cp, Object[] roots,
			Repository repository, RepositoryTreeNodeType type) {
		for (Object repo : roots) {
