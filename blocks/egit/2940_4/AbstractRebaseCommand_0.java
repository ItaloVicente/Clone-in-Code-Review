
	protected Ref getRef(Object selected) {
		if (selected instanceof RepositoryTreeNode<?>) {
			RepositoryTreeNode node = (RepositoryTreeNode) selected;
			if (node.getType() == RepositoryTreeNodeType.REF)
				return ((RefNode) node).getObject();
		}

		return null;
	}

	protected Repository getRepository(Object selected) {
		if (selected instanceof IProject)
			return getMapping((IProject) selected).getRepository();
		else if (selected instanceof RepositoryTreeNode<?>)
			return ((RepositoryTreeNode<?>) selected).getRepository();

		return null;
	}
