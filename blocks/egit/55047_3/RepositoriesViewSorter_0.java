		if (e1 instanceof RepositoryTreeNode
				&& e2 instanceof RepositoryTreeNode) {
			RepositoryTreeNode node1 = (RepositoryTreeNode) e1;
			RepositoryTreeNode node2 = (RepositoryTreeNode) e2;
			return node1.compareTo(node2);
		} else {
