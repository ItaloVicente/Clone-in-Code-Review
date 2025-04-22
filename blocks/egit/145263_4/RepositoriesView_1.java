		RepositoryTreeNode repoNode = findRepositoryNode(cp,
				cp.getElements(getCommonViewer().getInput()), repository);
		return repoNode == null ? null : findChild(cp, repoNode, type);
	}

	private RepositoryTreeNode findChild(ITreeContentProvider cp,
			RepositoryTreeNode root, RepositoryTreeNodeType type) {
		for (Object child : cp.getChildren(root)) {
			RepositoryTreeNode childNode = (RepositoryTreeNode) child;
			if (childNode.getType() == type) {
				return childNode;
			}
		}
		return null;
	}

	private RepositoryTreeNode findRepositoryNode(
			ITreeContentProvider cp, Object[] roots,
			Repository repository) {
		for (Object repo : roots) {
