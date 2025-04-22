
		RepositoryTreeNode node = (RepositoryTreeNode) element;

		switch (node.getType()) {
		case PROJECTS:
			return true;
		default:
			Object[] children = getChildren(element);
			return children != null && children.length > 0;

		}
