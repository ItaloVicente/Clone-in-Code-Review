	@SuppressWarnings("unchecked")
	private RepositoryTreeNode<Repository> getRepositoryNode() {
		if (myType == RepositoryTreeNodeType.REPO) {
			return (RepositoryTreeNode<Repository>) this;
		} else {
			return getParent().getRepositoryNode();
		}
	}

