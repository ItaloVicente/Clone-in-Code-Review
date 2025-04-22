				return findChild(cp, node, type);
			}
		}
		for (Object repo : roots) {
			RepositoryTreeNode node = (RepositoryTreeNode) repo;
			RepositoryTreeNode submodules = findChild(cp, node,
						RepositoryTreeNodeType.SUBMODULES);
			if (submodules != null) {
				RepositoryTreeNode childNode = recursiveRepositoryChildNode(cp,
						cp.getChildren(submodules), repository, type);
				if (childNode != null) {
					return childNode;
