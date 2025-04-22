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

